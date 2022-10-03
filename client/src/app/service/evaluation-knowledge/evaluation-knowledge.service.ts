import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EvaluationKnowledge } from '../../model/evaluation-knowledge';


const URL = 'http://localhost:8080/api/evaluationKnowledges/';

@Injectable({
  providedIn: 'root'
})

export class EvaluationKnowledgeService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<EvaluationKnowledge[]> {
    return this.http.get<EvaluationKnowledge[]>(URL + 'getAll');
  }

  getAllFromObject(id: number) {
    return this.http.get(URL + 'allEvaluationKnowledgeFromSubject/' + id);
  }

  getAllUndoneTests(studentOnTheYearId: number, subjectId: number): Observable<EvaluationKnowledge[]> {
    return this.http.get<EvaluationKnowledge[]>(URL + 'getAllUndoneTests/' + studentOnTheYearId + "/" + subjectId);
  }

  create(object: EvaluationKnowledge){
    return this.http.post(URL + 'create', object);
  }

  update(id : number, object : EvaluationKnowledge) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<EvaluationKnowledge>(URL + id);
  }
}
