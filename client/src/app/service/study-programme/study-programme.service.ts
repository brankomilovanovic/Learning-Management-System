import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StudyProgramme } from '../../model/study-programme';


const SP_URL = 'http://localhost:8080/api/studyProgrammes/';

@Injectable({
  providedIn: 'root'
})
export class StudyProgrammeService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<StudyProgramme[]> {
    return this.http.get<StudyProgramme[]>(SP_URL + 'allStudyProgrammes');
  }

  create(studyProgramme: StudyProgramme){
    return this.http.post(SP_URL + 'createStudyProgrammes', studyProgramme);
  }

  getByUsername(username : String): Observable<StudyProgramme[]>  {
    return this.http.get<StudyProgramme[]>(SP_URL + 'getByUsername/' + username);
  }

  existsByNameInFaculty(objectToChangeID : number, name: String, idFaculty: number) {
    return this.http.get(SP_URL + "existsByNameInFaculty/" + objectToChangeID + "/" + name + "/" + idFaculty);
  }

  update(id : number, studyProgramme : StudyProgramme) {
    return this.http.put(SP_URL + id, studyProgramme);
  }

  delete(id : number) {
    return this.http.delete(SP_URL + id);
  }

  getOne(id : number) {
    return this.http.get<StudyProgramme>(SP_URL + id);
  }


}
