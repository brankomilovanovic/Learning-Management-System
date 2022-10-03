import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClassTime } from 'src/app/model/class-time';

const URL = 'http://localhost:8080/api/classTimes/';

@Injectable({
  providedIn: 'root'
})
export class ClassTimeService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<ClassTime[]> {
    return this.http.get<ClassTime[]>(URL + 'getAll');
  }

  getByUsername(username : String): Observable<ClassTime[]>  {
    return this.http.get<ClassTime[]>(URL + 'findByUsername/' + username);
  }

  existsBySubjectID(objectToChangeID : number, id: number, typeTeachingId: number) {
    return this.http.get(URL + "existsBySubjectID/" + objectToChangeID + "/" + id + "/" + typeTeachingId);
  }
  
  create(object: ClassTime){
    return this.http.post(URL + 'create', object);
  }

  update(id : number, object : ClassTime) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<ClassTime>(URL + id);
  }
}
