import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeOfTopic } from 'src/app/model/type-of-topic';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'typeoftopic/';

@Injectable({
  providedIn: 'root'
})
export class TypeOfTopicService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<TypeOfTopic[]> {
    return this.http.get<TypeOfTopic[]>(URL + 'allTypeOfTopic');
  }
}
