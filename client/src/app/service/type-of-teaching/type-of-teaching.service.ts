import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeOfTeaching } from '../../model/type-of-teaching';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'typeofteaching/';

@Injectable({
  providedIn: 'root'
})

export class TypeOfTeachingService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<TypeOfTeaching[]> {
    return this.http.get<TypeOfTeaching[]>(URL + 'allTypeOfTeaching');
  }
}
