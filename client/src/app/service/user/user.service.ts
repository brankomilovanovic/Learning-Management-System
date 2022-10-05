import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../model/user';
import { map } from 'rxjs/internal/operators/map';
import { environment } from '../../../environments/environment.prod';

const API_URL = environment.apiUrl + 'menu/';
const USER_URL = environment.apiUrl + 'users/';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  constructor(private http: HttpClient) { }

  getAll(pageNo: number, pageSize: number, sortBy: string, name: string, surname: string, roleId: string) {
    const params = new HttpParams().set('pageNo', pageNo.toString()).set('pageSize', pageSize.toString()).set('sortBy', sortBy).set('name', name).set('surname', surname).set('roleId', roleId);
    return this.http.get<User[]>(USER_URL, {params});
  }

  getOne(username : string) {
    return this.http.get<User>(USER_URL + username);
  }
  
  create(user: User) {
    return this.http.post(USER_URL, user);
  }

  update(id : number, user : User) {
    return this.http.put(USER_URL + id, user);
  }

  delete(id: number) {
    return this.http.delete(USER_URL + id);
  }

  checkEmail(mail: string, id : string) {
    return this.http.get(USER_URL + "checkEmail/" + id + "/" + mail);
  }

  checkUsername(username: string, id : string) {
    return this.http.get(USER_URL + "checkUsername/" + id + "/" + username);
  }

  countUser(name: string, surname: string, roleId: string) {
    const params = new HttpParams().set('name', name).set('surname', surname).set('roleId', roleId);
    return this.http.get(USER_URL + "countUser", {params});
  }

  //JUST FOR TEST
  generate300Users() {
    return this.http.get(USER_URL + 'generate300Users');
  }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'professor', { responseType: 'text' });
  }
}
