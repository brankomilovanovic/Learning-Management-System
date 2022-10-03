import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { KorisnikNaForumu } from 'src/app/model/forum/korisnik-na-forumu';

const URL = 'http://localhost:8080/api/korisnik_na_forumu/';

@Injectable({
  providedIn: 'root'
})
export class KorisnikNaForumuService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<KorisnikNaForumu[]> {
    return this.http.get<KorisnikNaForumu[]>(URL);
  }

  getOne(id: number) {
    return this.http.get<KorisnikNaForumu>(URL + id);
  }

  getFollowTema(id: number, korisnik_id: number) {
    return this.http.get<KorisnikNaForumu>(URL + "getFollowTema/" + id + "/" + korisnik_id);
  }

  getFollowPodforum(id: number, korisnik_id: number) {
    return this.http.get<KorisnikNaForumu>(URL + "getFollowPodforum/" + id + "/" + korisnik_id);
  }

  getByUsername(username: String) {
    return this.http.get<KorisnikNaForumu>(URL + "getByUsername/" + username);
  }

  create(object: KorisnikNaForumu){
    return this.http.post(URL, object);
  }

  update(id : number, object : KorisnikNaForumu) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<KorisnikNaForumu>(URL + id);
  }

  getAllFollowTema(tema_id: number): Observable<KorisnikNaForumu[]> {
    return this.http.get<KorisnikNaForumu[]>(URL + "getAllFollowTema/" + tema_id);
  }

  getAllFollowPodforum(podforum_id: number): Observable<KorisnikNaForumu[]> {
    return this.http.get<KorisnikNaForumu[]>(URL + "getAllFollowPodforum/" + podforum_id);
  }
}
