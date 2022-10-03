import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Faculty } from '../../model/faculty';


const URL = 'http://localhost:8080/api/faculty/';

@Injectable({
  providedIn: 'root'
})

export class FacultyService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Faculty[]> {
    return this.http.get<Faculty[]>(URL + 'allFaculty');
  }

  getOne(id : number) {
    return this.http.get<Faculty>(URL + id);
  }

  getByUsername(username : String): Observable<Faculty[]>  {
    return this.http.get<Faculty[]>(URL + 'findByUsername/' + username);
  }

  existsFacultyNameInUniversity(objectToChangeID : number, nameFaculty: String, idUniversity: number) {
    return this.http.get(URL + "existsFacultyNameInUniversity/" + objectToChangeID + "/" + nameFaculty + "/" + idUniversity);
  }

  create(faculty: Faculty){
    return this.http.post(URL + 'createFaculty', faculty);
  }

  update(id : number, faculty : Faculty) {
    return this.http.put(URL + id, faculty);
  }

  delete(id : number) {
    return this.http.delete(URL + id);
  }
}
