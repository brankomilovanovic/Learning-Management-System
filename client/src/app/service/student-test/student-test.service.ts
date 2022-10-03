import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StudentTests } from 'src/app/model/student-tests';

const URL = 'http://localhost:8080/api/student_tests/';

@Injectable({
  providedIn: 'root'
})
export class StudentTestService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<StudentTests[]> {
    return this.http.get<StudentTests[]>(URL + 'alltests');
  }

  getByStudentOnTheYearAndSubject(studentOnTheYearId : number, subjectId: number) {
    return this.http.get<StudentTests>(URL + studentOnTheYearId + "/" + subjectId);
  }

  create(studentTests: StudentTests){
    return this.http.post(URL, studentTests);
  }

  update(id : number, studentTests : StudentTests) {
    return this.http.put(URL + id, studentTests);
  }

  delete(id : number) {
    return this.http.delete<StudentTests>(URL + id);
  }
}
