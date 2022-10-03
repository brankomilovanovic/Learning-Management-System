
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Year } from 'src/app/model/year';


const URL = 'http://localhost:8080/api/godinastudija/';
@Injectable({
  providedIn: 'root'
})
export class YearService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Year[]> {
    return this.http.get<Year[]>(URL + 'svegodine');
  }

  create(year: Year){
    return this.http.post(URL + 'createyear', year);
  }

  existsByStudyProgrammeAndDate(objectToChangeID : number, id: number, date: Date) {
    return this.http.get(URL + "existsByStudyProgrammeAndDate/" + objectToChangeID + "/" + id + "/" + date);
  }

  update(id : number, year : Year) {
    return this.http.put(URL + id, year);
  }

  delete(id : number) {
    return this.http.delete<Year>(URL + id);
  }
}
