import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../../model/topic';
import { environment } from '../../../environments/environment.prod';

const URL = environment.apiUrl + 'topics/';

@Injectable({
  providedIn: 'root'
})

export class TopicService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Topic[]> {
    return this.http.get<Topic[]>(URL + 'allTopics');
  }

  create(topic: Topic){
    return this.http.post(URL + 'createTopic', topic);
  }

  update(id : number, topic : Topic) {
    return this.http.put(URL + id, topic);
  }

  delete(id : number) {
    return this.http.delete<Topic>(URL + id);
  }

  existsByOpisWithTopicType(objectToChangeID : number, opis: String, topicTypeId: number) {
    return this.http.get(URL + "existsByOpisWithTopicType/" + objectToChangeID + "/" + opis + "/" + topicTypeId);
  }
}
