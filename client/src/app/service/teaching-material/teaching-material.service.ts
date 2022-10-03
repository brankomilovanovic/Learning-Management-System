import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TeachingMaterial } from '../../model/teaching-material';

const URL = 'http://localhost:8080/api/teaching_material/';

@Injectable({
  providedIn: 'root'
})
export class TeachingMaterialService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<TeachingMaterial[]> {
    return this.http.get<TeachingMaterial[]>(URL + 'getAll');
  }

  getAllAuthors(): Observable<String[]> {
    return this.http.get<String[]>(URL + 'getAllAuthors');
  }

  existsByNaziv(objectToChangeID : number, naziv: String) {
    return this.http.get(URL + "existsByNaziv/" + objectToChangeID + "/" + naziv);
  }

  create(object: TeachingMaterial){
    return this.http.post(URL + 'create', object);
  }

  update(id : number, object : TeachingMaterial) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete(URL + id);
  }
}
