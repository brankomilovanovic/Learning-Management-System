import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from '../../model/subject';
import { environment } from '../../../environments/environment.prod';


const URL = environment.apiUrl + 'subjects/';

@Injectable({
  providedIn: 'root'
})

export class SubjectService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Subject[]> {
    return this.http.get<Subject[]>(URL + 'allSubjects');
  }

  getOne(id: number) {
    return this.http.get<Subject>(URL + id);
  }

  create(subject: Subject){
    return this.http.post(URL + 'createSubject', subject);
  }

  update(id : number, subject : Subject) {
    return this.http.put(URL + id, subject);
  }

  delete(id : number) {
    return this.http.delete<Subject>(URL + id);
  }

  checkNaziv(naziv: string, id : string) {
    return this.http.get(URL + "checkNaziv/" + id + "/" + naziv);
  }
}
