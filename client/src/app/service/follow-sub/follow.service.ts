import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { followSub } from 'src/app/model/followSub';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'followsub/';

@Injectable({
  providedIn: 'root'
})
export class FollowService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<followSub[]> {
    return this.http.get<followSub[]>(URL + 'allfollow');
  }

  getAllUserForSubject(subjectId: number): Observable<number[]> {
    return this.http.get<number[]>(URL + 'findUserForSubject/'  + subjectId);
  }

  create(object: followSub){
    return this.http.post(URL + 'createfollow', object);
  }

  update(id : number, object : followSub) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<followSub>(URL + id);
  }

  getSubjects(id : number): Observable<followSub[]>{
    return this.http.get<followSub[]>(URL + 'subjects/' + id);
  }
  
}
