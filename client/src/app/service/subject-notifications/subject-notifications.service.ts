import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SubjectNotifications } from '../../model/subject-notifications';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'subject_notifications/';

@Injectable({
  providedIn: 'root'
})
export class SubjectNotificationsService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<SubjectNotifications[]> {
    return this.http.get<SubjectNotifications[]>(URL);
  }

  getBySubjectId(subjectId: number): Observable<SubjectNotifications[]> {
    return this.http.get<SubjectNotifications[]>(URL + subjectId);
  }

  create(object: SubjectNotifications){
    return this.http.post(URL, object);
  }

  update(id : number, object : SubjectNotifications) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<SubjectNotifications>(URL + id);
  }
}
