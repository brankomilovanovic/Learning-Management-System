import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeEvaluation } from '../../model/type-evaluation';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'typeEvaluations/';

@Injectable({
  providedIn: 'root'
})

export class TypeEvaluationService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<TypeEvaluation[]> {
    return this.http.get<TypeEvaluation[]>(URL + 'getAll');
  }
}
