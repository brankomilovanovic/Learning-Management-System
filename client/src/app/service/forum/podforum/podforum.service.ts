import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Podforum } from '../../../model/forum/podforum';

const URL = 'http://localhost:8080/api/podforum/';

@Injectable({
  providedIn: 'root'
})
export class PodforumService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Podforum[]> {
    return this.http.get<Podforum[]>(URL);
  }

  getOne(id: number) {
    return this.http.get<Podforum>(URL + id);
  }

  create(object: Podforum){
    return this.http.post(URL, object);
  }

  update(id : number, object : Podforum) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<Podforum>(URL + id);
  }

  existByName(objectToChangeID : number, name: String) {
    return this.http.get(URL + "existByNaziv/" + objectToChangeID + "/" + name);
  }
}
