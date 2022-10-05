import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'mail/';

@Injectable({
  providedIn: 'root'
})
export class MailService {

  constructor(private http: HttpClient) { }

  sendEmail(toEmail: String, subject: String, body: String){
    return this.http.get(URL + toEmail + "/" + subject + "/" + body);
  }
}
