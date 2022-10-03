import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'src/app/model/subject';
import { TeacherOnRealization } from 'src/app/model/teacher-on-realization';
import { Location } from '@angular/common';
import { SubjectService } from 'src/app/service/subject/subject.service';
import { TeacherOnRealizationService } from 'src/app/service/teacher-on-realization/teacher-on-realization.service';
import { SubjectRealizationService } from 'src/app/service/subject-realization/subject-realization.service';

@Component({
  selector: 'app-create-edit-subject-realization',
  templateUrl: './create-edit-subject-realization.component.html',
  styleUrls: ['./create-edit-subject-realization.component.css']
})
export class CreateEditSubjectRealizationComponent implements OnInit {

  isFailed = false;
  errorMessage = '';

  subjectRealizationToChangeID: number = -1;

  teacherOnRealizations: TeacherOnRealization[] = [];
  subjects: Subject[] = [];
  
  formCreateEditSubjectRealization : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "subject" : new FormControl(null, [Validators.required]),
    "teacherOnRealization" : new FormControl(null, [Validators.required])
  });
    
  constructor(private teacherOnRealizationService : TeacherOnRealizationService, private location: Location, private subjectRealization: SubjectRealizationService,
    private subjectService: SubjectService, private route: ActivatedRoute) {}

  ngOnInit(): void {

    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditSubjectRealization.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
      this.subjectRealizationToChangeID = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["id"];
    }

    this.teacherOnRealizationService.getAll().subscribe((objects: TeacherOnRealization[]) => { this.teacherOnRealizations = objects; })
    this.subjectService.getAll().subscribe((objects: Subject[]) => { this.subjects = objects; });
  }

  cancel(){
    this.formCreateEditSubjectRealization.get("id")?.setValue(null);
    this.formCreateEditSubjectRealization.reset();
    this.location.back();
  }

  save(){
    if(this.formCreateEditSubjectRealization.valid){
      if(this.formCreateEditSubjectRealization.value.id == null){
        this.subjectRealization.create(this.formCreateEditSubjectRealization.value).subscribe(x => { this.cancel(); });
      } else {
        this.subjectRealization.update(this.formCreateEditSubjectRealization.value.id, this.formCreateEditSubjectRealization.value).subscribe(x => { this.cancel(); });
      }
    }
  }

  checkProfessorExistForThisSubject(){
    if(this.formCreateEditSubjectRealization.value.subject != null && this.formCreateEditSubjectRealization.value.teacherOnRealization != null){
      for(let typeOfTeaching of this.formCreateEditSubjectRealization.value.teacherOnRealization.typeOfTeaching){
        this.subjectRealization.existsSubjectByTypeOfTeaching(this.subjectRealizationToChangeID, this.formCreateEditSubjectRealization.value.subject.id, typeOfTeaching.id).subscribe(data => {
          this.isFailed = false;
          this.formCreateEditSubjectRealization.controls.subject.setErrors(null);
          this.formCreateEditSubjectRealization.controls.teacherOnRealization.setErrors(null);
        }, err => {
          this.formCreateEditSubjectRealization.controls.subject.setErrors({'incorrect': true});
          this.formCreateEditSubjectRealization.controls.teacherOnRealization.setErrors({'incorrect': true});
          this.errorMessage = err.error.message;
          this.isFailed = true;
        });
      }
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
