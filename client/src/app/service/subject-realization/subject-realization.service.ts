import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SubjectRealization } from '../../model/subject-realization';

const URL = 'http://localhost:8080/api/subjectRealization/';

@Injectable({
  providedIn: 'root'
})

export class SubjectRealizationService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<SubjectRealization[]> {
    return this.http.get<SubjectRealization[]>(URL + 'allSubjectRealization');
  }

  getByUsername(username : String): Observable<SubjectRealization[]> {
    return this.http.get<SubjectRealization[]>(URL + 'findByUsername/' + username);
  }
  
  existsSubjectByTypeOfTeaching(objectToChangeID : number, subjectId: number, typeOfTeachingId: number) {
    return this.http.get(URL + "existsSubjectByTypeOfTeaching/" + objectToChangeID + "/" + subjectId + "/" + typeOfTeachingId);
  }

  create(subjectRealization: SubjectRealization){
    return this.http.post(URL + 'create', subjectRealization);
  }

  update(id : number, subjectRealization : SubjectRealization) {
    return this.http.put(URL + id, subjectRealization);
  }

  delete(id : number) {
    return this.http.delete<SubjectRealization>(URL + id);
  }
}
