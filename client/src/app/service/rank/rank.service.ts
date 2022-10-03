import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rank } from 'src/app/model/rank';

const URL = 'http://localhost:8080/api/ranks/';

@Injectable({
  providedIn: 'root'
})

export class RankService {

  constructor(private http: HttpClient) { }

  create(rank: Rank){
    return this.http.post(URL + 'createRank', rank);
  }

  update(id : number, rank : Rank) {
    return this.http.put(URL + id, rank);
  }

  getAll(): Observable<Rank[]> {
    return this.http.get<Rank[]>(URL + 'allRanks');
  }

  getByUsername(username : String): Observable<Rank[]>  {
    return this.http.get<Rank[]>(URL + 'findByUsername/' + username);
  }

  delete(id : number) {
    return this.http.delete<Rank>(URL + id);
  }

}
