import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StudentOnTheYear } from 'src/app/model/student-on-the-year';
import { StudentService } from 'src/app/model/student-service';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'studentnagodini/';

@Injectable({
  providedIn: 'root'
})
export class StudentOnTheYearService {

  constructor(private http: HttpClient) { }

  getOne(id : number) {
    return this.http.get<StudentOnTheYear>(URL + "student/" + id);
  }

  getSubjectsByID(id : number): Observable<StudentService[]>{
    return this.http.get<StudentService[]>(URL + 'upisi/' + id);
  }

  getAll(): Observable<StudentOnTheYear[]> {
    return this.http.get<StudentOnTheYear[]>(URL + 'svinagodini');
  }

  existIndexNo(objectToChangeID : number, id: String) {
    return this.http.get(URL + "existIndex/" + objectToChangeID + "/" + id);
  }

  existsStudent(objectToChangeID : number, idStudent: number) {
    return this.http.get(URL + "existsStudent/" + objectToChangeID + "/" + idStudent);
  }

  create(year: StudentOnTheYear){
    return this.http.post(URL + 'createstudentonyear', year);
  }

  update(id : number, year : StudentOnTheYear) {
    return this.http.put(URL + id, year);
  }

  delete(id : number) {
    return this.http.delete<StudentOnTheYear>(URL + id);
  }
}
