import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/model/user';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { UserService } from 'src/app/service/user/user.service';
import { StudentService } from 'src/app/service/student/student.service';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { Professor } from 'src/app/model/professor';
import { Student } from 'src/app/model/student';
import { City, Country } from 'country-state-city';
import { Adress } from 'src/app/model/adress';
import { Router } from '@angular/router';
import { NgxNotificationMsgService, NgxNotificationStatusMsg } from 'ngx-notification-msg';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: User = undefined;
  userAdress: Adress = undefined;
  roles: String[] = [];
  kliknut = false;
  lozinka: String = null;

  //address
  countrys : String[] = [];
  citys : String[] = [];

  full : boolean;
  isFailed = false;
  errorMessage = '';

  formCreateEditUser : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "email" : new FormControl(null, [Validators.required]),
    "name" : new FormControl(null, [Validators.required]),
    "password" : new FormControl(null, [Validators.required]),
    "surname" : new FormControl(null, [Validators.required]),
    "username" : new FormControl(null, [Validators.required]),
    "roles" : new FormControl(null),
  });

  formCreateEditProfessorAndStudent : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "jmbg" : new FormControl(null),
    "dateOfBirth" : new FormControl(null),
    "address" : new FormControl(null),
    "phoneNumber" : new FormControl(null),
    "biography" : new FormControl(null),
    "user" : new FormControl(null)
  });

  formCreateEditAddress : FormGroup = new FormGroup({
    "country" : new FormControl("Serbia", null),
    "city" : new FormControl(null, [Validators.required]),
    "streetAndNumber" : new FormControl(null)
  });

  constructor(private token: TokenStorageService, public us: UserService, private ss:StudentService, private ps: ProfessorService,
    private router: Router, private readonly ngxNotificationMsgService: NgxNotificationMsgService) { }

  ngOnInit(): void {
    const currentUser = this.token.getUser();

    if(currentUser.roles.includes('ROLE_PROFESSOR')){
      this.ps.getByUsername(currentUser.username).subscribe((professor : Professor) => {
        this.userAdress = professor.address;
        this.formCreateEditProfessorAndStudent.patchValue(professor);
        this.formCreateEditAddress.get("country").setValue(professor.address.country);
        this.formCreateEditAddress.get("city").setValue(professor.address.city);
        this.formCreateEditAddress.get("streetAndNumber").setValue(professor.address.streetAndNumber);
        this.selectCountry(professor.address.country);
      });
      this.full = true
    } 

    if(currentUser.roles.includes('ROLE_STUDENT')){
      this.ss.getByUsername(currentUser.username).subscribe((student : Student) => {
        this.formCreateEditProfessorAndStudent.patchValue(student);
        this.formCreateEditAddress.get("country").setValue(student.address.country);
        this.formCreateEditAddress.get("city").setValue(student.address.city);
        this.formCreateEditAddress.get("streetAndNumber").setValue(student.address.streetAndNumber);
        this.userAdress = this.formCreateEditAddress.value;
        this.selectCountry(student.address.country);
      });
      this.full = true
    }

    this.us.getOne(currentUser.username).subscribe((user : User) => {
      console.log(user)
      this.user = user
      this.formCreateEditUser.patchValue(user);
      for(let role of user.roles){
        if(role.name.toString() == "ROLE_ADMINISTRATOR"){ this.roles.push("Administrator"); } 
        else if(role.name.toString() == "ROLE_PROFESSOR"){ this.roles.push("Professor"); } 
        else if(role.name.toString() == "ROLE_STUDENT"){ this.roles.push("Student"); } 
        else if(role.name.toString() == "ROLE_USER"){ this.roles.push("User"); } 
        else { this.roles.push(role.name.toString());}
      }

      for(let country of Country.getAllCountries()){  // get all country and city for that country
        this.countrys.push(country.name);
        if(country.name == this.formCreateEditAddress.value.country) {
          for(let city of City.getCitiesOfCountry(country.isoCode)) { this.citys.push(city.name); };
        }
      };
      
    });
  };

  saveUser(){
    if(this.formCreateEditUser.valid){
      if(this.lozinka != null) { this.formCreateEditUser.patchValue({["password"] : this.lozinka}); } // Kada unese novu lozinku polje vise nije prazno i setujemo novu lozinku u formu
      
      //Logout when user change username
      if(this.formCreateEditUser.value["username"] != this.user.username ){
        this.us.update(this.formCreateEditUser.value.id, this.formCreateEditUser.value).subscribe(x => {
          this.token.signOut();
          window.location.href="login"
        });
      } else {
        this.us.update(this.formCreateEditUser.value.id, this.formCreateEditUser.value).subscribe(x => {
          this.us.getOne(this.user.username).subscribe((user : User) => {
            this.user = user;
            this.formCreateEditUser.patchValue(user);
            this.ngxNotificationMsgService.open({
              status: NgxNotificationStatusMsg.SUCCESS,
              header: 'Edit profile',
              messages: ['You have successfully edited profile.']
            });
          });
        });
      }

      if(this.roles.includes("Professor") || this.roles.includes("Student")) {
      this.formCreateEditProfessorAndStudent.value.user = this.formCreateEditUser.value; // set user from form
      this.formCreateEditProfessorAndStudent.value.address = this.formCreateEditAddress.value; // set address object form form
      this.userAdress = this.formCreateEditAddress.value;
        if(this.roles.includes("Professor")){
          this.ps.update(this.formCreateEditProfessorAndStudent.value.id, this.formCreateEditProfessorAndStudent.value).subscribe();
        }
        if(this.roles.includes("Student")){
          this.ss.update(this.formCreateEditProfessorAndStudent.value.id, this.formCreateEditProfessorAndStudent.value).subscribe();
        }
      }
    } else {
      this.errorMessage = "You can fill in all fields, where star!";
      this.isFailed = true;
    }
  };

  selectCountry(selectedCountry: string){
    for(let country of Country.getAllCountries()){  // get all country and city for that country
      if(country.name == selectedCountry) {
        this.citys = [];
        for(let city of City.getCitiesOfCountry(country.isoCode)){ this.citys.push(city.name); };
      }
    };
  }

  checkU(){
    if(this.formCreateEditUser.get("username").valid == true){
      this.us.checkUsername(this.formCreateEditUser.value.username, this.formCreateEditUser.value.id).subscribe(data => {
        this.isFailed = false;
        this.formCreateEditUser.controls.username.setErrors(null);
      }, err => {
        this.formCreateEditUser.controls.username.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

  checkEmail(){
    if(this.formCreateEditUser.get("email").valid == true){
      this.us.checkEmail(this.formCreateEditUser.value.email, this.formCreateEditUser.value.id).subscribe(data => {
        this.isFailed = false;
        this.formCreateEditUser.controls.email.setErrors(null);
      }, err => {
        this.formCreateEditUser.controls.email.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

  details(){
    this.kliknut = true;
    this.myFunction();
  }
  
  hideDet(){
    this.kliknut = false;
  }
  
  myFunction() {
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 7000);
  }

}