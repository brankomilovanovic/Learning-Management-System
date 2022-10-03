import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EducationGoal } from 'src/app/model/education-goal';

const URL = 'http://localhost:8080/api/educationGoals/';

@Injectable({
  providedIn: 'root'
})
export class EducationGoalService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<EducationGoal[]> {
    return this.http.get<EducationGoal[]>(URL + 'getAll');
  }

  existsByTopicID(objectToChangeID : number, id: number) {
    return this.http.get(URL + "existsByTopicID/" + objectToChangeID + "/" + id);
  }

  existsByOpis(objectToChangeID : number, opis: String) {
    return this.http.get(URL + "existsByOpis/" + objectToChangeID + "/" + opis);
  }
  
  create(object: EducationGoal){
    return this.http.post(URL + 'create', object);
  }

  update(id : number, object : EducationGoal) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<EducationGoal>(URL + id);
  }
}
