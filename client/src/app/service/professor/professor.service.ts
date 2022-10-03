import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Professor } from '../../model/professor';

const PROF_URL = 'http://localhost:8080/api/professors/';

@Injectable({
  providedIn: 'root'
})

export class ProfessorService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Professor[]> {
    return this.http.get<Professor[]>(PROF_URL + 'allProfessors');
  }

  create(professor: Professor){
    return this.http.post(PROF_URL + 'createProfessor', professor);
  }

  update(id : number, professor : Professor) {
    return this.http.put(PROF_URL + id, professor);
  }

  delete(id : number) {
    return this.http.delete(PROF_URL + id);
  }

  getOne(id : number) {
    return this.http.get<Professor>(PROF_URL + id);
  }

  getByUsername(username : String) {
    return this.http.get<Professor>(PROF_URL + "getByUsername/" + username);
  }

  checkJmbg(jmbg: string, id : string) {
    return this.http.get(PROF_URL + "checkJmbg/" + id + "/" + jmbg);
  }
}
