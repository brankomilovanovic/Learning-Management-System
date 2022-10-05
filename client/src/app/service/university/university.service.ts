import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { University } from '../../model/university';
import { environment } from '../../../environments/environment.prod';


const SP_URL = environment.apiUrl + 'universities/';

@Injectable({
  providedIn: 'root'
})
export class UniversityService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<University[]> {
    return this.http.get<University[]>(SP_URL + 'allUniversities');
  }

  getAllByUsername(username: string): Observable<University[]> {
    return this.http.get<University[]>(SP_URL + 'getByUsername'+ "/" + username);
  }

  create(university: University){
    return this.http.post(SP_URL + 'createUniversity', university);
  }

  existByName(objectToChangeID : number, name: String) {
    return this.http.get(SP_URL + "existByName/" + objectToChangeID + "/" + name);
  }
  
  update(id : number, university : University) {
    return this.http.put(SP_URL + id, university);
  }

  delete(id : number) {
    return this.http.delete(SP_URL + id);
  }

  getOne(id : number) {
    return this.http.get<University>(SP_URL + id);
  }


}
