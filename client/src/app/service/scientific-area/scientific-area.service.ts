import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ScientificArea } from '../../model/scientific-area';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'scientificAreas/';

@Injectable({
  providedIn: 'root'
})
export class ScientificAreaService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<ScientificArea[]> {
    return this.http.get<ScientificArea[]>(URL + 'allScientificAreas');
  }

  existByName(objectToChangeID : number, name: String) {
    return this.http.get(URL + "existScientificArea/" + objectToChangeID + "/" + name);
  }

  create(scientificAreas: ScientificArea){
    return this.http.post(URL + 'createScientificAreas', scientificAreas);
  }

  update(id : number, scientificAreas : ScientificArea) {
    return this.http.put(URL + id, scientificAreas);
  }

  delete(id : number) {
    return this.http.delete<ScientificArea>(URL + id);
  }
}
