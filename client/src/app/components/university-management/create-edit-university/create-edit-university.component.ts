import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Professor } from 'src/app/model/professor';
import { Faculty } from 'src/app/model/faculty';
import { UniversityService } from 'src/app/service/university/university.service';
import { FacultyService } from 'src/app/service/faculty/faculty.service';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { StudyProgrammeService } from 'src/app/service/study-programme/study-programme.service';
import { StudyProgramme } from 'src/app/model/study-programme';
import { Country, City } from 'country-state-city';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-create-edit-university',
  templateUrl: './create-edit-university.component.html',
  styleUrls: ['./create-edit-university.component.css']
})
export class CreateEditUniversityComponent implements OnInit {

  headmasters: Professor[] = [];
  faculty: Faculty[] = [];
  studyProgrammes: StudyProgramme[] = [];

  isFailed = false;
  errorMessage = '';

  universityToChangeID: number = -1;
  role : String = null;

  //address
  countrys : String[] = [];
  citys : String[] = [];

  formCreateEditUniversity : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "address" : new FormControl(null),
    "contactDetails" : new FormControl(null, [Validators.required]),
    "description" : new FormControl(null),
    "establishmentDate" : new FormControl(null),
    "name" : new FormControl(null, [Validators.required]),
    "faculty" : new FormControl(null),
    "headmaster" : new FormControl(null, [Validators.required])
  });

  formCreateEditAddress : FormGroup = new FormGroup({
    "country" : new FormControl("Serbia", null),
    "city" : new FormControl(null),
    "streetAndNumber" : new FormControl(null)
  });
  constructor(private route: ActivatedRoute, private location: Location, private studyProgrammeService: StudyProgrammeService, private universityService: UniversityService, private professorService : ProfessorService, 
    private facultyService : FacultyService, private tokenStorageService: TokenStorageService) { }
  
    ngOnInit(): void {

      const user = this.tokenStorageService.getUser();
      if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }

      for(let country of Country.getAllCountries()){  // get all country and city for that country
        this.countrys.push(country.name);
        if(country.name == this.formCreateEditAddress.value.country) {
          for(let city of City.getCitiesOfCountry(country.isoCode)) { this.citys.push(city.name); };
        }
      };

      if(window.location.href.indexOf("id") > -1) {
        this.universityToChangeID = JSON.parse(this.route.snapshot.paramMap.get('universityForChange'))["id"];
        this.universityService.getOne(JSON.parse(this.route.snapshot.paramMap.get('universityForChange'))["id"]).subscribe(university => { 
          this.formCreateEditAddress.patchValue({["country"] : university.address.country});
          this.formCreateEditAddress.patchValue({["city"] : university.address.city})
          this.formCreateEditAddress.patchValue({["streetAndNumber"] : university.address.streetAndNumber})
          this.formCreateEditUniversity.patchValue(university); 
          this.selectCountry(university.address.country);
        });
      }
  
      this.professorService.getAll().subscribe((professor: Professor[]) => { this.headmasters = professor; })
      this.facultyService.getAll().subscribe((faculty: Faculty[]) => { this.faculty = faculty; })
      this.studyProgrammeService.getAll().subscribe((studyProgramme: StudyProgramme[]) => { this.studyProgrammes = studyProgramme; })
    
  }

  save(){
    if(this.formCreateEditUniversity.valid){
      if(this.formCreateEditUniversity.value["id"] == null){
        this.formCreateEditUniversity.value.address = this.formCreateEditAddress.value;
        this.universityService.create(this.formCreateEditUniversity.value).subscribe(rank => { this.cancel(); });
      } else {
        this.formCreateEditUniversity.value.address = this.formCreateEditAddress.value;
        this.universityService.update(this.formCreateEditUniversity.value.id, this.formCreateEditUniversity.value).subscribe(rank => { this.cancel(); });
      }
    }
  }

  cancel(){
    this.formCreateEditUniversity.get("id")?.setValue(null);
    this.formCreateEditUniversity.reset();
    this.location.back();
  }

  comparator(faculty1: any, faculty2: any) {
    return faculty1 && faculty2
      ? faculty1.id === faculty2.id
      : faculty1 === faculty2;
  }

  selectCountry(selectedCountry: string){
    for(let country of Country.getAllCountries()){  // get all country and city for that country
      if(country.name == selectedCountry) {
        this.citys = [];
        for(let city of City.getCitiesOfCountry(country.isoCode)){ this.citys.push(city.name); };
      }
    };
  }

  checkName(){
    if(this.formCreateEditUniversity.value.name != null){
      this.universityService.existByName(this.universityToChangeID, this.formCreateEditUniversity.value.name).subscribe(data => {
        this.isFailed = false;
        this.formCreateEditUniversity.controls.name.setErrors(null);
      }, err => {
        this.formCreateEditUniversity.controls.name.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

}
