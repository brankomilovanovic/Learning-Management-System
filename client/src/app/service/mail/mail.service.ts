import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const URL = 'http://localhost:8080/api/mail/';

@Injectable({
  providedIn: 'root'
})
export class MailService {

  constructor(private http: HttpClient) { }

  sendEmail(toEmail: String, subject: String, body: String){
    return this.http.get(URL + toEmail + "/" + subject + "/" + body);
  }
}
