import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Topic } from 'src/app/model/topic';
import { TypeOfTeaching } from 'src/app/model/type-of-teaching';
import { TopicService } from 'src/app/service/topic/topic.service';
import { Location } from '@angular/common';
import { TypeOfTeachingService } from 'src/app/service/type-of-teaching/type-of-teaching.service';
import { ClassTimeService } from 'src/app/service/class-time/class-time.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectRealization } from 'src/app/model/subject-realization';
import { SubjectRealizationService } from 'src/app/service/subject-realization/subject-realization.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { ClassTime } from 'src/app/model/class-time';

@Component({
  selector: 'app-create-edit-class-time',
  templateUrl: './create-edit-class-time.component.html',
  styleUrls: ['./create-edit-class-time.component.css']
})
export class CreateEditClassTimeComponent implements OnInit {

  isFaild = false;
  errorMessage = '';

  topics: Topic[] = [];
  typeOfTeachings: TypeOfTeaching[] = [];
  subjectRealizations: SubjectRealization[] = [];

  classTimeToChangeID: number = -1;

  formCreateEditClassTime : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "vremePocetka" : new FormControl(null, [Validators.required]),
    "vremeKraja" : new FormControl(null, [Validators.required]),
    "topic" : new FormControl(null),
    "typeOfTeaching" : new FormControl(null, [Validators.required]),
    "subjectRealization" : new FormControl(null, [Validators.required])
  });

  constructor(private topicService : TopicService, private route: ActivatedRoute, private location: Location, private typeOfTeachingService: TypeOfTeachingService,
    private classTimeService: ClassTimeService, private subjectRealizationService: SubjectRealizationService, private tokenStorageService: TokenStorageService) {}

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditClassTime.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
      this.classTimeToChangeID = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["id"];
      console.log(this.formCreateEditClassTime.value)
    }

    this.topicService.getAll().subscribe((objects: Topic[]) => { 
      for(let topic of objects){
        if(topic.topicType.naziv.toString() == "TERMIN_NASTAVE"){
          this.topics.push(topic);
        }
      }
    });

    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_PROFESSOR')){
      this.subjectRealizationService.getByUsername(user.username).subscribe((objects: SubjectRealization[]) => { this.subjectRealizations = objects; });
    } else {
      this.subjectRealizationService.getAll().subscribe((objects: SubjectRealization[]) => { this.subjectRealizations = objects; });
    }

    this.typeOfTeachingService.getAll().subscribe((objects: TypeOfTeaching[]) => { this.typeOfTeachings = objects; });
  }

  cancel(){
    this.formCreateEditClassTime.get("id")?.setValue(null);
    this.formCreateEditClassTime.reset();
    this.location.back();
  }

  save(){
    if(this.formCreateEditClassTime.valid){
      if(this.formCreateEditClassTime.value.id == null){
        this.classTimeService.create(this.formCreateEditClassTime.value).subscribe(x => { this.cancel(); });
      } else {
        this.classTimeService.update(this.formCreateEditClassTime.value.id, this.formCreateEditClassTime.value).subscribe(x => { this.cancel(); });
      }
    }
  }

  checkClassTimeExist(){
    if(this.formCreateEditClassTime.value.subjectRealization != null && this.formCreateEditClassTime.value.typeOfTeaching != null){
      this.classTimeService.existsBySubjectID(this.classTimeToChangeID, this.formCreateEditClassTime.value.subjectRealization.subject.id, this.formCreateEditClassTime.value.typeOfTeaching.id).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditClassTime.controls.subjectRealization.setErrors(null);
        this.formCreateEditClassTime.controls.typeOfTeaching.setErrors(null);
      }, err => {
        this.formCreateEditClassTime.controls.subjectRealization.setErrors({'incorrect': true});
        this.formCreateEditClassTime.controls.typeOfTeaching.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }

  comparator(v1: any, v2: any) {
    if(v1 && v2)
    {
      return v1["id"] == v2["id"]
    } else {
      return  v1 == v2
    }
  }

}
