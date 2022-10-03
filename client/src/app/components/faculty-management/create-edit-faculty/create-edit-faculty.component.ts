import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Professor } from 'src/app/model/professor';
import { StudyProgramme } from 'src/app/model/study-programme';
import { University } from 'src/app/model/university';
import { FacultyService } from 'src/app/service/faculty/faculty.service';
import { StudyProgrammeService } from 'src/app/service/study-programme/study-programme.service';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { UniversityService } from 'src/app/service/university/university.service';
import { Country, City }  from 'country-state-city';
import { Faculty } from 'src/app/model/faculty';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-create-edit-faculty',
  templateUrl: './create-edit-faculty.component.html',
  styleUrls: ['./create-edit-faculty.component.css']
})
export class CreateEditFacultyComponent implements OnInit {

  isFaild = false;
  errorMessage = '';

  facultyToChangeID: number = -1;
  role : String = "ROLE_ADMINISTRATOR";

  deans: Professor[] = [];
  universitys: University[] = [];
  studyProgrammess: StudyProgramme[] = [];

  //address
  countrys : String[] = [];
  citys : String[] = [];

  formCreateEditFaculty : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "address" : new FormControl(null),
    "contactDetails" : new FormControl(null, [Validators.required]),
    "description" : new FormControl(null),
    "name" : new FormControl(null, [Validators.required]),
    "dean" : new FormControl(null, [Validators.required]),
    "studyProgrammes" : new FormControl(null),
    "university" : new FormControl(null, [Validators.required])
  });

  formCreateEditAddress : FormGroup = new FormGroup({
    "country" : new FormControl("Serbia", null),
    "city" : new FormControl(null),
    "streetAndNumber" : new FormControl(null)
  });

  constructor(private route: ActivatedRoute, private location: Location, private studyProgrammeService: StudyProgrammeService, private professorService : ProfessorService, 
    private facultyService : FacultyService, private universityService : UniversityService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_PROFESSOR')){
      this.role = "ROLE_PROFESSOR"
    }

    for(let country of Country.getAllCountries()){  // get all country and city for that country
      this.countrys.push(country.name);
      if(country.name == this.formCreateEditAddress.value.country) {
        for(let city of City.getCitiesOfCountry(country.isoCode)) { this.citys.push(city.name); };
      }
    };

    if(window.location.href.indexOf("id") > -1) {
      this.facultyToChangeID = JSON.parse(this.route.snapshot.paramMap.get('facultyForChange'))["id"];
      this.facultyService.getOne(JSON.parse(this.route.snapshot.paramMap.get('facultyForChange'))["id"]).subscribe(faculty => { 
        this.formCreateEditAddress.patchValue({["country"] : faculty.address.country});
        this.formCreateEditAddress.patchValue({["city"] : faculty.address.city})
        this.formCreateEditAddress.patchValue({["streetAndNumber"] : faculty.address.streetAndNumber})
        this.formCreateEditFaculty.patchValue(faculty); 
        this.selectCountry(faculty.address.country);
      });
    }
    this.professorService.getAll().subscribe((professor: Professor[]) => { this.deans = professor; })
    this.studyProgrammeService.getAll().subscribe((studyProgramme: StudyProgramme[]) => { this.studyProgrammess = studyProgramme; })
    this.universityService.getAll().subscribe((university: University[]) => { this.universitys = university; })
  
}
  
  save(){
    if(this.formCreateEditFaculty.valid){
      if(this.formCreateEditFaculty.value["id"] == null){
        this.formCreateEditFaculty.value.address = this.formCreateEditAddress.value;
        this.facultyService.create(this.formCreateEditFaculty.value).subscribe(rank => { this.cancel(); });
      } else {
        this.formCreateEditFaculty.value.address = this.formCreateEditAddress.value;
        this.facultyService.update(this.formCreateEditFaculty.value.id, this.formCreateEditFaculty.value).subscribe(rank => { this.cancel(); });
      
      }
    }
  }

  cancel(){
    this.formCreateEditFaculty.get("id")?.setValue(null);
    this.formCreateEditFaculty.reset();
    this.location.back();
  }

  comparator(studyProgramme1: any, studyProgramme2: any) {
    return studyProgramme1 && studyProgramme2
      ? studyProgramme1.id === studyProgramme2.id
      : studyProgramme1 === studyProgramme2;
  }

  selectCountry(selectedCountry: string){
    for(let country of Country.getAllCountries()){  // get all country and city for that country
      if(country.name == selectedCountry) {
        this.citys = [];
        for(let city of City.getCitiesOfCountry(country.isoCode)){ this.citys.push(city.name); };
      }
    };
  }

  checkFacultyNameExist(){
    if(this.formCreateEditFaculty.value.name != null && this.formCreateEditFaculty.get("university").valid == true){
      this.facultyService.existsFacultyNameInUniversity(this.facultyToChangeID, this.formCreateEditFaculty.value.name, this.formCreateEditFaculty.value.university.id).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditFaculty.controls.name.setErrors(null);
      }, err => {
        this.formCreateEditFaculty.controls.name.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }

}
