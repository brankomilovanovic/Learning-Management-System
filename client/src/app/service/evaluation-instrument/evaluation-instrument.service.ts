import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EvaluationInstrument } from 'src/app/model/evaluation-instrument';

const URL = 'http://localhost:8080/api/evaluationInstruments/';

@Injectable({
  providedIn: 'root'
})
export class EvaluationInstrumentService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<EvaluationInstrument[]> {
    return this.http.get<EvaluationInstrument[]>(URL + 'getAll');
  }

  existsByFileID(objectToChangeID : number, id: number) {
    return this.http.get(URL + "existsByFileID/" + objectToChangeID + "/" + id);
  }

  existsByTipTestiranja(objectToChangeID : number, tipTestiranja: String) {
    return this.http.get(URL + "existsByTipTestiranja/" + objectToChangeID + "/" + tipTestiranja);
  }

  create(object: EvaluationInstrument){
    return this.http.post(URL + 'create', object);
  }

  update(id : number, object : EvaluationInstrument) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<EvaluationInstrument>(URL + id);
  }
}
