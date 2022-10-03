import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TeacherOnRealization } from '../../model/teacher-on-realization';

const URL = 'http://localhost:8080/api/teacherOnRealizations/';

@Injectable({
  providedIn: 'root'
})

export class TeacherOnRealizationService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<TeacherOnRealization[]> {
    return this.http.get<TeacherOnRealization[]>(URL + 'allTeacherOnRealizations');
  }

  getByProfessorId(id: number) {
    return this.http.get(URL + 'getByProfessorId/' + id);
  }

  create(teacherOnRealization: TeacherOnRealization){
    return this.http.post(URL + 'createTeacherOnRealizations', teacherOnRealization);
  }

  update(id : number, teacherOnRealization : TeacherOnRealization) {
    return this.http.put(URL + id, teacherOnRealization);
  }

  delete(id : number) {
    return this.http.delete<TeacherOnRealization>(URL + id);
  }
}
