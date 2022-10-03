import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tema } from '../../../model/forum/tema';

const URL = 'http://localhost:8080/api/tema/';

@Injectable({
  providedIn: 'root'
})
export class TemaService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Tema[]> {
    return this.http.get<Tema[]>(URL);
  }

  getAllByPodforum(id: number): Observable<Tema[]> {
    return this.http.get<Tema[]>(URL + "getAllByPodforumId/" + id);
  }

  getOne(id: number) {
    return this.http.get<Tema>(URL + id);
  }

  create(object: Tema){
    return this.http.post(URL, object);
  }

  dodajPregledTemi(id : number) {
    return this.http.get(URL + "dodajPregledTemi/" + id);
  }

  update(id : number, object : Tema) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<Tema>(URL + id);
  }
}
