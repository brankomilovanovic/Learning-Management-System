import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeEvaluation } from '../../model/type-evaluation';

const URL = 'http://localhost:8080/api/typeEvaluations/';

@Injectable({
  providedIn: 'root'
})

export class TypeEvaluationService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<TypeEvaluation[]> {
    return this.http.get<TypeEvaluation[]>(URL + 'getAll');
  }
}
