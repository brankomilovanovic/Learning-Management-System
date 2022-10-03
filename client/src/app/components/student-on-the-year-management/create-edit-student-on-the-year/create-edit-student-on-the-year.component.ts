import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Student } from 'src/app/model/student';
import { StudentOnTheYearService } from 'src/app/service/student-on-the-year/student-on-the-year.service';
import { StudentService } from 'src/app/service/student/student.service';
import { YearService } from 'src/app/service/yearofstudy/year.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-create-edit-student-on-the-year',
  templateUrl: './create-edit-student-on-the-year.component.html',
  styleUrls: ['./create-edit-student-on-the-year.component.css']
})
export class CreateEditStudentOnTheYearComponent implements OnInit {

  isFailed = false;
  errorMessage = '';

  studentOnTheYearChangeID: number = -1;
  students : Student[] = [];

  formCreateEditStudentOnTheYear : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "indexNo" : new FormControl(null, [Validators.required]),
    "dateOfEntry" : new FormControl(null, [Validators.required]),
    "student" : new FormControl(null, [Validators.required])
  });

  constructor(private route: ActivatedRoute, private location:Location,
    private studentService: StudentService, private yearofstudyService: YearService,
     private studentontheyearService: StudentOnTheYearService) { }

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditStudentOnTheYear.patchValue(JSON.parse(this.route.snapshot.paramMap.get('studentForChange')));
      this.studentOnTheYearChangeID = JSON.parse(this.route.snapshot.paramMap.get('studentForChange'))["id"];
    }
    
    this.studentService.getAll().subscribe((students : Student[]) => {
      this.students = students
    });
  }
  
  save(){
    if(this.formCreateEditStudentOnTheYear.valid){
      if(this.formCreateEditStudentOnTheYear.value["id"] == null){
        this.studentontheyearService.create(this.formCreateEditStudentOnTheYear.value).subscribe(x => { this.cancel(); });
      } else {
        this.studentontheyearService.update(this.formCreateEditStudentOnTheYear.value.id, this.formCreateEditStudentOnTheYear.value).subscribe(x => { this.cancel(); });
      }
    }
  };

  cancel(){
    this.formCreateEditStudentOnTheYear.get("id")?.setValue(null);
    this.formCreateEditStudentOnTheYear.reset();
    this.location.back();
  }

  compare(topic1: any, topic2: any) {
    return topic1 && topic2
      ? topic1.id === topic2.id
      : topic1 === topic2;
  }

  checkIndexNo(){
    if(this.formCreateEditStudentOnTheYear.get("indexNo").valid == true){
      this.studentontheyearService.existIndexNo(this.studentOnTheYearChangeID, this.formCreateEditStudentOnTheYear.value.indexNo).subscribe(data => {
        this.isFailed = false;
        this.formCreateEditStudentOnTheYear.controls.indexNo.setErrors(null);
      }, err => {
        this.formCreateEditStudentOnTheYear.controls.indexNo.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

 checkStudentOnYear(){
    if(this.formCreateEditStudentOnTheYear.value.student != null && this.formCreateEditStudentOnTheYear.value.dateOfEntry != null){
      this.studentontheyearService.existsStudent(this.studentOnTheYearChangeID, this.formCreateEditStudentOnTheYear.value.student.id).subscribe(data => {
        this.isFailed = false;
        this.formCreateEditStudentOnTheYear.controls.student.setErrors(null);
      }, err => {
        this.formCreateEditStudentOnTheYear.controls.student.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

}
