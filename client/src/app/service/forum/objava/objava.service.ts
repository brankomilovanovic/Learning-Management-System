import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Objava } from '../../../model/forum/objava';
import { environment } from '../../../../environments/environment.prod';

const URL = environment.apiUrl + 'objava/';

@Injectable({
  providedIn: 'root'
})
export class ObjavaService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Objava[]> {
    return this.http.get<Objava[]>(URL);
  }

  getAllForTema(tema_id: number): Observable<Objava[]> {
    return this.http.get<Objava[]>(URL + "getAllForTema/" + tema_id);
  }

  getOne(id: number) {
    return this.http.get<Objava>(URL + id);
  }

  getLastPostForSubforum(id: number) {
    return this.http.get<Objava>(URL + "getLastPostForSubforum/" + id);
  }

  getLastPostForTopic(id: number) {
    return this.http.get<Objava>(URL + "getLastPostForTopic/" + id);
  }

  getFirstObjavaForTopic(id: number) {
    return this.http.get<Objava>(URL + "getFirstObjavaForTopic/" + id);
  }

  create(object: Objava){
    return this.http.post(URL, object);
  }

  update(id : number, object : Objava) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<Objava>(URL + id);
  }
}
