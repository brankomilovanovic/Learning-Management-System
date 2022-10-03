import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { YearService } from 'src/app/service/yearofstudy/year.service';
import { StudyProgramme } from 'src/app/model/study-programme';
import { StudyProgrammeService } from 'src/app/service/study-programme/study-programme.service';
import { SubjectService } from 'src/app/service/subject/subject.service';
import { Subject } from 'src/app/model/subject';


@Component({
  selector: 'app-create-edit-year',
  templateUrl: './create-edit-year.component.html',
  styleUrls: ['./create-edit-year.component.css']
})
export class CreateEditYearComponent implements OnInit {

  isFailed = false;
  errorMessage = '';

  yearToChangeID: number = -1;

  studyP: StudyProgramme[] = [];
  sub: Subject[] = [];

  formCreateEditYear : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "year" : new FormControl(null, [Validators.required]),
    "active" : new FormControl(true),
    "subjects" : new FormControl(null, [Validators.required]),
    "studyProgramme" : new FormControl(null, [Validators.required])
  });

  constructor(private route: ActivatedRoute, private location: Location, private yearService: YearService,
    private s:StudyProgrammeService, private sp:SubjectService) { 
    }

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditYear.patchValue(JSON.parse(this.route.snapshot.paramMap.get('yearForChange')));
      this.yearToChangeID = JSON.parse(this.route.snapshot.paramMap.get('yearForChange'))["id"];
    }

    this.sp.getAll().subscribe((sp: Subject[]) => { this.sub = sp; })
    this.s.getAll().subscribe((sp: StudyProgramme[]) => { this.studyP = sp; })
  }

  saveYear(){
    if(this.formCreateEditYear.valid){
      if(this.formCreateEditYear.value["id"] == null){
        this.yearService.create(this.formCreateEditYear.value).subscribe(year => { this.cancel(); });
      } else {
        this.yearService.update(this.formCreateEditYear.value.id, this.formCreateEditYear.value).subscribe(year => { this.cancel(); });
      }
    }
  };

  cancel(){
    this.formCreateEditYear.get("id")?.setValue(null);
    this.formCreateEditYear.reset();
    this.location.back();
  }

  comparator(year1: any, year2: any) {
    return year1 && year2
      ? year1.id === year2.id
      : year1 === year2;
  }
  
  checkStudyProgrammeAndDate(){
    if(this.formCreateEditYear.value.studyProgramme != null && this.formCreateEditYear.value.year != null){
      this.yearService.existsByStudyProgrammeAndDate(this.yearToChangeID, this.formCreateEditYear.value.studyProgramme.id, this.formCreateEditYear.value.year).subscribe(data => {
        this.isFailed = false;
        this.formCreateEditYear.controls.studyProgramme.setErrors(null);
        this.formCreateEditYear.controls.year.setErrors(null);
      }, err => {
        this.formCreateEditYear.controls.studyProgramme.setErrors({'incorrect': true});
        this.formCreateEditYear.controls.year.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

}
