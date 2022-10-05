import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ERole, Role } from '../../model/role';
import { environment } from '../../../environments/environment.prod';

const ROLE_URL = environment.apiUrl + 'roles/';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Role[]> {
    return this.http.get<Role[]>(ROLE_URL + `allRoles`);
  }

  getByName(role_name: string) {
    return this.http.get<Role>(ROLE_URL + role_name);
  }

  countAdministrator(){
    return this.http.get(ROLE_URL + "countAdministrator");
  }
}
