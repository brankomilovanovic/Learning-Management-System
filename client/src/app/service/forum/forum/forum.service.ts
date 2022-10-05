import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Forum } from '../../../model/forum/forum';
import { environment } from '../../../../environments/environment.prod';

const URL = environment.apiUrl + 'forum/';

@Injectable({
  providedIn: 'root'
})
export class ForumService {

  constructor(private http: HttpClient) { }

  getForum() {
    return this.http.get<Forum>(URL + "getForum");
  }

  updateVidljivost(vidljivost : boolean) {
    return this.http.get(URL + "updateVidljivost/" + vidljivost);
  }

  getOne(id: number) {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Forum>(URL + "getOne", {params});
  }
}
