import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeOfTopic } from 'src/app/model/type-of-topic';

const URL = 'http://localhost:8080/api/typeoftopic/';

@Injectable({
  providedIn: 'root'
})
export class TypeOfTopicService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<TypeOfTopic[]> {
    return this.http.get<TypeOfTopic[]>(URL + 'allTypeOfTopic');
  }
}
