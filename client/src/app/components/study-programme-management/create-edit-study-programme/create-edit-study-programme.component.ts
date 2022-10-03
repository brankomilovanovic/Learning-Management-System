import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { StudyProgrammeService } from 'src/app/service/study-programme/study-programme.service';
import { Professor } from 'src/app/model/professor';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { FacultyService } from 'src/app/service/faculty/faculty.service';
import { Faculty } from 'src/app/model/faculty';
import { Year } from 'src/app/model/year';
import { YearService } from 'src/app/service/yearofstudy/year.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-create-edit-study-programme',
  templateUrl: './create-edit-study-programme.component.html',
  styleUrls: ['./create-edit-study-programme.component.css']
})
export class CreateEditStudyProgrammeComponent implements OnInit {

  isFaild = false;
  errorMessage = '';

  directors: Professor[] = [];
  facultys: Faculty[] = [];
  year: Year[] = [];

  studyProgrammeToChangeID: number = -1;
  role: String = "ROLE_ADMINISTRATOR";

  formCreateEditStudyProgramme : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "description" : new FormControl(null),
    "name" : new FormControl(null, [Validators.required]),
    "director" : new FormControl(null, [Validators.required]),
    "faculty" : new FormControl(null, [Validators.required])
  });
  
  constructor(private route: ActivatedRoute, private location: Location, private studyProgrammeService: StudyProgrammeService, private professorService : ProfessorService, 
    private facultyService : FacultyService, private yearService: YearService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_PROFESSOR')){
      this.role = "ROLE_PROFESSOR"
    }

    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditStudyProgramme.patchValue(JSON.parse(this.route.snapshot.paramMap.get('studyProgrammeForChange')));
      this.studyProgrammeToChangeID = JSON.parse(this.route.snapshot.paramMap.get('studyProgrammeForChange'))["id"];
    }

    this.professorService.getAll().subscribe((professor: Professor[]) => { this.directors = professor; })
    this.facultyService.getAll().subscribe((faculty: Faculty[]) => { this.facultys = faculty; })
    this.yearService.getAll().subscribe((year: Year[]) => { this.year = year; })
  }
  
  save(){
    if(this.formCreateEditStudyProgramme.valid){
      if(this.formCreateEditStudyProgramme.value["id"] == null){
        this.studyProgrammeService.create(this.formCreateEditStudyProgramme.value).subscribe(rank => { this.cancel(); });
      } else {
        this.studyProgrammeService.update(this.formCreateEditStudyProgramme.value.id, this.formCreateEditStudyProgramme.value).subscribe(rank => { this.cancel(); });
      }
    }
  }

  cancel(){
    this.formCreateEditStudyProgramme.get("id")?.setValue(null);
    this.formCreateEditStudyProgramme.reset();
    this.location.back();
  }

  comparator(year1: any, year2: any) {
    return year1 && year2
      ? year1.id === year2.id
      : year1 === year2;
  }

  checkExistsByNameInFaculty(){
    if(this.formCreateEditStudyProgramme.value.name != null && this.formCreateEditStudyProgramme.value.faculty != null){
      this.studyProgrammeService.existsByNameInFaculty(this.studyProgrammeToChangeID, this.formCreateEditStudyProgramme.value.name, this.formCreateEditStudyProgramme.value.faculty.id).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditStudyProgramme.controls.name.setErrors(null);
        this.formCreateEditStudyProgramme.controls.faculty.setErrors(null);
      }, err => {
        this.formCreateEditStudyProgramme.controls.name.setErrors({'incorrect': true});
        this.formCreateEditStudyProgramme.controls.faculty.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }
  
}
