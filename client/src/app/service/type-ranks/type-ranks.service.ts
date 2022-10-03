import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeRanks } from '../../model/type-ranks';


const URL = 'http://localhost:8080/api/typeRanks/';

@Injectable({
  providedIn: 'root'
})

export class TypeRanksService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<TypeRanks[]> {
    return this.http.get<TypeRanks[]>(URL + 'allRanks');
  }

  existByName(objectToChangeID : number, name: String) {
    return this.http.get(URL + "existByName/" + objectToChangeID + "/" + name);
  }

  create(typeRanks: TypeRanks){
    return this.http.post(URL + 'createRanks', typeRanks);
  }

  update(id : number, typeRanks : TypeRanks) {
    return this.http.put(URL + id, typeRanks);
  }

  delete(id : number) {
    return this.http.delete<TypeRanks>(URL + id);
  }
}
