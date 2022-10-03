import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TopicService } from 'src/app/service/topic/topic.service';
import { Location } from '@angular/common';
import { TypeOfTopic } from 'src/app/model/type-of-topic';
import { TypeOfTopicService } from 'src/app/service/type-of-topic/type-of-topic.service';
import { TeachingMaterial } from 'src/app/model/teaching-material';
import { TeachingMaterialService } from 'src/app/service/teaching-material/teaching-material.service';

@Component({
  selector: 'app-craete-edit-topic',
  templateUrl: './craete-edit-topic.component.html',
  styleUrls: ['./craete-edit-topic.component.css']
})
export class CraeteEditTopicComponent implements OnInit {

  isFailed = false;
  errorMessage = '';

  topicTypes: TypeOfTopic[] = [];
  topicType: String = '';
  teachingMaterials: TeachingMaterial[] = [];

  topicToChangeID: number = -1;

  formCreateEditTopic : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "opis" : new FormControl(null, [Validators.required]),
    "topicType" : new FormControl(null, [Validators.required]),
    "teachingMaterial" : new FormControl(null)
  });

  constructor(private route: ActivatedRoute, private location: Location, private topicService: TopicService,
    private typeOfTopicService: TypeOfTopicService, private teachingMaterialService: TeachingMaterialService) { }

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditTopic.patchValue(JSON.parse(this.route.snapshot.paramMap.get('topicForChange')));
      this.topicToChangeID = JSON.parse(this.route.snapshot.paramMap.get('topicForChange'))["id"];
      this.checkExistsByOpisWithTopicType();
    }

    this.typeOfTopicService.getAll().subscribe((objects: TypeOfTopic[]) => { this.topicTypes = objects; });
    this.teachingMaterialService.getAll().subscribe((objects: TeachingMaterial[]) => { this.teachingMaterials = objects; });
  }

  save(){
    if(this.formCreateEditTopic.valid){
      if(this.formCreateEditTopic.value["id"] == null){
        this.topicService.create(this.formCreateEditTopic.value).subscribe(x => { this.cancel(); });
      } else {
        this.topicService.update(this.formCreateEditTopic.value.id, this.formCreateEditTopic.value).subscribe(x => { this.cancel(); });
      }
    }
  };

  cancel(){
    this.formCreateEditTopic.get("id")?.setValue(null);
    this.formCreateEditTopic.reset();
    this.location.back();
  }

  checkExistsByOpisWithTopicType(){
    if(this.formCreateEditTopic.value.opis != null && this.formCreateEditTopic.value.topicType != null){
      this.topicType = this.formCreateEditTopic.value.topicType.naziv;
      this.topicService.existsByOpisWithTopicType(this.topicToChangeID, this.formCreateEditTopic.value.opis, this.formCreateEditTopic.value.topicType.id).subscribe(data => {
        this.isFailed = false;
        this.formCreateEditTopic.controls.opis.setErrors(null);
        this.formCreateEditTopic.controls.topicType.setErrors(null);
      }, err => {
        this.formCreateEditTopic.controls.opis.setErrors({'incorrect': true});
        this.formCreateEditTopic.controls.topicType.setErrors(null);
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

  compare(topic1: any, topic2: any) {
    return topic1 && topic2
      ? topic1.id === topic2.id
      : topic1 === topic2;
  }
  
}
