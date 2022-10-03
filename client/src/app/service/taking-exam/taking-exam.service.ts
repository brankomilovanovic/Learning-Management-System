import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TakingExam } from 'src/app/model/taking-exam';

const URL = 'http://localhost:8080/api/takingExam/';

@Injectable({
  providedIn: 'root'
})
export class TakingExamService {

  constructor(private http: HttpClient) { }

  getByStudentOnTheYearAndSubject(studentOnTheYearId : number, subjectId: number): Observable<TakingExam[]> {
    return this.http.get<TakingExam[]>(URL + 'findByStudentOnTheYearAndSubject/' + studentOnTheYearId + "/" + subjectId);
  }

  create(object: TakingExam){
    return this.http.post(URL + 'create', object);
  }
}
