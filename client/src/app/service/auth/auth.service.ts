import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.prod';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }
  
  login(credentials): Observable<any> {
    return this.http.post(environment.apiUrl + 'auth/login', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user): Observable<any> {
    return this.http.post(environment.apiUrl + 'auth/registration', {
      name: user.name,
      surname : user.surname,
      username: user.username,
      email: user.email,
      password: user.password
    }, httpOptions);
  }
}
