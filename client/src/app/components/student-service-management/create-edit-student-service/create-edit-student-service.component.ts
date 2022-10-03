import { Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { StudyProgramme } from 'src/app/model/study-programme';
import { Year } from 'src/app/model/year';
import { Location } from '@angular/common';
import { YearService } from 'src/app/service/yearofstudy/year.service';
import { StudyProgrammeService } from 'src/app/service/study-programme/study-programme.service';
import { StudentOnTheYear } from 'src/app/model/student-on-the-year';
import { StudentOnTheYearService } from 'src/app/service/student-on-the-year/student-on-the-year.service';
import { StudentServiceService } from 'src/app/service/student-service/student-service.service';

@Component({
  selector: 'app-create-edit-student-service',
  templateUrl: './create-edit-student-service.component.html',
  styleUrls: ['./create-edit-student-service.component.css']
})
export class CreateEditStudentServiceComponent implements OnInit {
  
  isFailed = false;
  errorMessage = '';

  yearToChangeID: number = -1;

  student: StudentOnTheYear[]=[];
  programme: StudyProgramme[]=[];
  year: Year []=[];
  godina:Year[]=[];

  constructor(private route: ActivatedRoute, private location: Location,private yearService: YearService,
    private s:StudyProgrammeService, private stu:StudentOnTheYearService, private service: StudentServiceService
    ,private yearof: YearService) {  }

  formCreateEditStudent : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "student" : new FormControl(null, [Validators.required]),
    "studyProgrammes" : new FormControl(null, [Validators.required]),
    "year" : new FormControl(null, [Validators.required])
  });  

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditStudent.patchValue(JSON.parse(this.route.snapshot.paramMap.get('studentForChange')));
      this.yearToChangeID = JSON.parse(this.route.snapshot.paramMap.get('studentForChange'))["id"];
    }

    this.stu.getAll().subscribe((sp: StudentOnTheYear[]) => { this.student = sp; });
    this.yearof.getAll().subscribe((sp: Year[]) => { this.godina = sp; });
    this.s.getAll().subscribe((sp: StudyProgramme[]) => { 
      this.programme = sp;
      if(this.formCreateEditStudent.value.studyProgrammes != null){
        this.selectProgramme();
      }
    }); 
  }

  selectProgramme(){
    for(let program of this.programme){  
      if(program.name == this.formCreateEditStudent.value.studyProgrammes.name) {
        this.year = []
        for(let y of program.yearOfStudy){ 
          this.year.push(y); 
        };
      }
    };
  }

  saveStudent(){
    if(this.formCreateEditStudent.valid){
      if(this.formCreateEditStudent.value["id"] == null){
        this.service.create(this.formCreateEditStudent.value).subscribe(year => { this.cancel(); });
      } else {
        this.service.update(this.formCreateEditStudent.value.id, this.formCreateEditStudent.value).subscribe(year => { this.cancel(); });
      }
    }
  };
    
  cancel(){
    this.formCreateEditStudent.get("id")?.setValue(null);
    this.formCreateEditStudent.reset();
    this.location.back();
  }

  comparator(year1: any, year2: any) {
    return year1 && year2
      ? year1.id === year2.id
      : year1 === year2;
  }

  checkExistsStudentByStudyProgrammeAndYear(){
    if(this.formCreateEditStudent.value.student != null &&this.formCreateEditStudent.value.studyProgrammes != null && this.formCreateEditStudent.value.year != null){
      for(let i of this.formCreateEditStudent.value.year) {
        this.service.existsStudentByStudyProgrammeAndYear(this.yearToChangeID, this.formCreateEditStudent.value.student.id, this.formCreateEditStudent.value.studyProgrammes.id, i.id).subscribe(data => {
          this.isFailed = false;
          this.formCreateEditStudent.controls.studyProgrammes.setErrors(null);
          this.formCreateEditStudent.controls.year.setErrors(null);
        }, err => {
          this.errorMessage = err.error.message;
          this.formCreateEditStudent.controls.studyProgrammes.setErrors({'incorrect': true});
          this.formCreateEditStudent.controls.year.setErrors({'incorrect': true});
          this.isFailed = true;
        });
      }
    }
  }
}
