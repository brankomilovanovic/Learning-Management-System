import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { History } from '../../model/history';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'history/';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<History[]> {
    return this.http.get<History[]>(URL + 'allhistory');
  }

  create(object: History){
    return this.http.post(URL + 'createHistory', object);
  }

  update(id : number, object : History) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<History>(URL + id);
  }
}
