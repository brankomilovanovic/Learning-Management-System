import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from '../../model/student';
import { environment } from '../../../environments/environment.prod';

const STUD_URL = environment.apiUrl + 'students/';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Student[]> {
    return this.http.get<Student[]>(STUD_URL + 'allStudents');
  }

  create(student: Student){
    return this.http.post(STUD_URL + 'createStudent', student);
  }

  readNotification(student: Student){
    return this.http.post(STUD_URL + 'read_notification', student);
  }

  update(id : number, student : Student) {
    return this.http.put(STUD_URL + id, student);
  }

  delete(id : number) {
    return this.http.delete(STUD_URL + id);
  }

  getOne(id : number) {
    return this.http.get<Student>(STUD_URL + id);
  }

  getByUsername(username : String) {
    return this.http.get<Student>(STUD_URL + "getByUsername/" + username);
  }

  checkJmbg(jmbg: string, id : string) {
    return this.http.get(STUD_URL + "checkJmbg/" + id + "/" + jmbg);
  }
  
}
