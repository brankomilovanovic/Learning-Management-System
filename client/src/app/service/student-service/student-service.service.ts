import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StudentService } from 'src/app/model/student-service';
import { environment } from '../../../environments/environment.prod';


const URL = environment.apiUrl + 'studentservice/';

@Injectable({
  providedIn: 'root'
})
export class StudentServiceService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<StudentService[]> {
    return this.http.get<StudentService[]>(URL + 'svistudentiservice');
  }

  existsStudentByStudyProgrammeAndYear(objectToChangeID : number, studentID: number, studyProgrammeId: number, yearOfStudyId: number) {
    return this.http.get(URL + "existsStudentByStudyProgrammeAndYear/" + objectToChangeID + "/" + studentID + "/" + studyProgrammeId + "/" + yearOfStudyId);
  }

  create(year: StudentService){
    return this.http.post(URL + 'createstudentservice', year);
  }

  update(id : number, year : StudentService) {
    return this.http.put(URL + id, year);
  }

  delete(id : number) {
    return this.http.delete<StudentService>(URL + id);
  }

  getSubjects(username : String): Observable<StudentService[]>{
    return this.http.get<StudentService[]>(URL + 'following/' + username);
  }

  getSubjectsByID(id : number): Observable<StudentService[]>{
    return this.http.get<StudentService[]>(URL + 'following_id/' + id);
  }

  
}
