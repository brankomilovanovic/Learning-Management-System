import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { EducationGoalService } from 'src/app/service/education-goal/education-goal.service';
import { Location } from '@angular/common';
import { Topic } from 'src/app/model/topic';
import { TopicService } from 'src/app/service/topic/topic.service';

@Component({
  selector: 'app-create-edit-education-goal',
  templateUrl: './create-edit-education-goal.component.html',
  styleUrls: ['./create-edit-education-goal.component.css']
})
export class CreateEditEducationGoalComponent implements OnInit {

  isFaild = false;
  errorMessage = '';
  
  topics: Topic[] = [];

  educationGoalChangeID: number = -1;

  formCreateEditEducationGoal : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "opis" : new FormControl(null, [Validators.required]),
    "topic" : new FormControl(null, [Validators.required])
  });
    
  constructor(private educationGoalService : EducationGoalService, private location: Location, private route: ActivatedRoute,
    private topicService: TopicService) {}

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditEducationGoal.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
      this.educationGoalChangeID = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["id"];

    }

    this.topicService.getAll().subscribe((objects: Topic[]) => { 
      for(let topic of objects){
        if(topic.topicType.naziv.toString() == "TERMIN_NASTAVE"){
          this.topics.push(topic);
        }
      }
    });
  }

  cancel(){
    this.formCreateEditEducationGoal.get("id")?.setValue(null);
    this.formCreateEditEducationGoal.reset();
    this.location.back();
  }

  save(){
    if(this.formCreateEditEducationGoal.valid){
      if(this.formCreateEditEducationGoal.value.id == null){
        this.educationGoalService.create(this.formCreateEditEducationGoal.value).subscribe(x => { this.cancel(); });
      } else {
        this.educationGoalService.update(this.formCreateEditEducationGoal.value.id, this.formCreateEditEducationGoal.value).subscribe(x => { this.cancel(); });
      }
    }
  }

  compare(topic1: any, topic2: any) {
    return topic1 && topic2
      ? topic1.id === topic2.id
      : topic1 === topic2;
  }

  checkEducationGoalExistWithTopic(topic : Topic){
    if(this.formCreateEditEducationGoal.get("topic").valid == true){
      this.educationGoalService.existsByTopicID(this.educationGoalChangeID, topic.id).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditEducationGoal.controls.topic.setErrors(null);
      }, err => {
        this.formCreateEditEducationGoal.controls.topic.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }

  checkEducationGoalOpisExist(opis : String){
    if(this.formCreateEditEducationGoal.get("opis").valid == true){
      this.educationGoalService.existsByOpis(this.educationGoalChangeID, opis).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditEducationGoal.controls.opis.setErrors(null);
      }, err => {
        this.formCreateEditEducationGoal.controls.opis.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }

}
