import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { SubjectService } from 'src/app/service/subject/subject.service';
import { Topic } from 'src/app/model/topic';
import { TopicService } from 'src/app/service/topic/topic.service';

@Component({
  selector: 'app-create-edit-subject',
  templateUrl: './create-edit-subject.component.html',
  styleUrls: ['./create-edit-subject.component.css']
})
export class CreateEditSubjectComponent implements OnInit {

  isCreateFailed = false;
  errorMessage = '';

  topics : Topic[] = [];

  formCreateEditSubject : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "naziv" : new FormControl(null, [Validators.required]),
    "espb" : new FormControl(null, [Validators.required]),
    "obavezan" : new FormControl(true, [Validators.required]),
    "brojPredavanja" : new FormControl(null, [Validators.required]),
    "brojVezbi" : new FormControl(null, [Validators.required]),
    "drugiObliciNastave" : new FormControl(null, [Validators.required]),
    "istrazivackiRad" : new FormControl(null, [Validators.required]),
    "ostaliCasovi" : new FormControl(null, [Validators.required]),
    "topic" : new FormControl(null)
  });

  constructor(private route: ActivatedRoute, private formBuilder:FormBuilder, private location: Location, private topicService: TopicService, private subjectService: SubjectService) { }

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditSubject.patchValue(JSON.parse(this.route.snapshot.paramMap.get('subjectForChange')));
      this.formCreateEditSubject.patchValue({"obavezan": JSON.parse(this.route.snapshot.paramMap.get('subjectForChange'))['obavezanBoolean']});
    }

    this.topicService.getAll().subscribe((objects: Topic[]) => { 
      for(let topic of objects){
        if(topic.topicType.naziv.toString() == "PREDMET"){
          this.topics.push(topic);
        }
      }
    });
  }

  save(){
    if(this.formCreateEditSubject.valid){
      if(this.formCreateEditSubject.value["id"] == null){
        this.subjectService.create(this.formCreateEditSubject.value).subscribe(x => { this.cancel(); });
      } else {
        this.subjectService.update(this.formCreateEditSubject.value.id, this.formCreateEditSubject.value).subscribe(x => { this.cancel(); });
      }
    }
  };

  cancel(){
    this.location.back();
    this.formCreateEditSubject.get("id")?.setValue(null);
    this.formCreateEditSubject.reset();
  }

  compare(topic1: any, topic2: any) {
    return topic1 && topic2
      ? topic1.id === topic2.id
      : topic1 === topic2;
  }

  checkSubjectNaziv(){
    if(this.formCreateEditSubject.get("naziv").valid == true){
      this.subjectService.checkNaziv(this.formCreateEditSubject.value.naziv, this.formCreateEditSubject.value.id).subscribe(data => {
        this.isCreateFailed = false;
        this.errorMessage = '';
        this.formCreateEditSubject.controls.naziv.setErrors(null);
      }, err => {
        this.formCreateEditSubject.controls.naziv.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isCreateFailed = true;
      });
    }
  }

}
