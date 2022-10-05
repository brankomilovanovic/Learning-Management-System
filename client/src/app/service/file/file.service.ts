import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { File } from 'src/app/model/file';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'files/';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<File[]> {
    return this.http.get<File[]>(URL + 'getAll');
  }
  
  existFileByURL(objectToChangeID : number, url: String) {
    return this.http.get(URL + "existFileByURL/" + objectToChangeID + "/" + url);
  }

  create(object: File){
    return this.http.post(URL + 'create', object);
  }

  update(id : number, object : File) {
    return this.http.put(URL + id, object);
  }

  delete(id : number) {
    return this.http.delete<File>(URL + id);
  }
}
