import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/service/user/user.service';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { RoleService } from 'src/app/service/role/role.service';
import { StudentService } from 'src/app/service/student/student.service';
import { Role } from '../../../model/role';
import { Location } from '@angular/common';
import { Country, City }  from 'country-state-city';
import { TypeRanks } from 'src/app/model/type-ranks';
import { TypeRanksService } from 'src/app/service/type-ranks/type-ranks.service';
import { ScientificArea } from 'src/app/model/scientific-area';
import { ScientificAreaService } from 'src/app/service/scientific-area/scientific-area.service';
import { RankService } from 'src/app/service/rank/rank.service';
import { MatTableDataSource } from '@angular/material/table';
import { Rank } from 'src/app/model/rank';
import { MatAccordion } from '@angular/material/expansion';

@Component({
  selector: 'app-create-edit-user',
  templateUrl: './create-edit-user.component.html',
  styleUrls: ['./create-edit-user.component.css']
})

export class CreateEditUserComponent implements OnInit {

  @ViewChild(MatAccordion) accordion: MatAccordion;
  
  roles: Role[] = [];
  isSuccessful = false;
  isSignUpFailed = false;
  isCreateRankFailed = false;
  isSuccessfulEdit = false;
  isEditFailed = false;
  errorMessage = '';
  hidePassword = true;
  selectRole = null;
  firstRole = [];
  checkJmbg = true;
  checkUser = true;
  userEditRankInList = false;
  userEditRankInListID = null;
  checkRolesExistProfessorsOrStudentsBoolean = false;
  checkRolesIsStudent = false;
  //address
  countrys : String[] = [];
  citys : String[] = [];
  //ranks
  typeRanksAll: TypeRanks[] = [];
  scientificAreas: ScientificArea[] = [];
  //table for ranks professor
  displayedColumns = ['typeRanks', 'scientificArea', 'actions'];
  dataSource: MatTableDataSource<Rank>;
  newListRanks : Rank[] = [];

  formCreateEditUser : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "name" : new FormControl(null, [Validators.required]),
    "surname" : new FormControl(null, [Validators.required]),
    "username" : new FormControl(null, [Validators.required]),
    "email" : new FormControl(null, [Validators.required, Validators.email]),
    "password" : new FormControl(null, [Validators.required]),
    "roles" : new FormControl(null, [Validators.required])
  });

  formCreateEditProfessorAndStudent : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "jmbg" : new FormControl(null, [Validators.required]),
    "dateOfBirth" : new FormControl(null),
    "address" : new FormControl(null),
    "phoneNumber" : new FormControl(null),
    "biography" : new FormControl(null),
    "user" : new FormControl(null)
  });

  formCreateEditAddress : FormGroup = new FormGroup({
    "country" : new FormControl("Serbia", null),
    "city" : new FormControl(null),
    "streetAndNumber" : new FormControl(null)
  });

  formCreateEditRanks : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "electionDate" : new FormControl(null),
    "terminationDate" : new FormControl(null),
    "typeRanks" : new FormControl(null),
    "scientificArea" : new FormControl(null),
    "professor" : new FormControl(null)
  });

  constructor(private route: ActivatedRoute, private userService : UserService, private roleService : RoleService, private professorService : ProfessorService, 
              private location: Location, private studentService: StudentService, private typeRanksService: TypeRanksService,
              private scientificAreaService: ScientificAreaService, private rankService: RankService) { }

  ngOnInit(): void {
    for(let country of Country.getAllCountries()){  // get all country and city for that country
      this.countrys.push(country.name);
      if(country.name == this.formCreateEditAddress.value.country) {
        for(let city of City.getCitiesOfCountry(country.isoCode)) { this.citys.push(city.name); };
      }
    };

    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditUser.patchValue(JSON.parse(this.route.snapshot.paramMap.get('userForChange')));
      if(this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_PROFESSOR")) { // if user professor set professor param from database
        this.professorService.getOne(JSON.parse(this.route.snapshot.paramMap.get('userForChange'))["id"]).subscribe(professor => { 
          this.formCreateEditAddress.patchValue({["country"] : professor.address.country});
          this.formCreateEditAddress.patchValue({["city"] : professor.address.city})
          this.formCreateEditAddress.patchValue({["streetAndNumber"] : professor.address.streetAndNumber})
          this.formCreateEditProfessorAndStudent.patchValue(professor); 
          this.selectCountry(professor.address.country);
          this.refreshRanksTable();
        });
      }

      if(this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_STUDENT")) {// if user student set student param from database
        this.studentService.getOne(JSON.parse(this.route.snapshot.paramMap.get('userForChange'))["id"]).subscribe(student => { 
          this.formCreateEditAddress.patchValue({["country"] : student.address.country});
          this.formCreateEditAddress.patchValue({["city"] : student.address.city})
          this.formCreateEditAddress.patchValue({["streetAndNumber"] : student.address.streetAndNumber})
          this.formCreateEditProfessorAndStudent.patchValue(student); 
          this.selectCountry(student.address.country);
        });
      }      
      this.firstRole = this.formCreateEditUser.value.roles;
      this.refreshCheckRolesProfessorAndStudent(this.formCreateEditUser.value.roles)
    }

    this.roleService.getAll().subscribe((role: Role[]) => { this.roles = role; });
    
    this.typeRanksService.getAll().subscribe((typeRanks: TypeRanks[]) => { 
      for(let typeRank of typeRanks){
        if(typeRank.active == true){
          this.typeRanksAll.push(typeRank); 
        }
      }
    });

    this.scientificAreaService.getAll().subscribe((scientificAreas: ScientificArea[]) => { 
      for(let scientificArea of scientificAreas) {  
        if(scientificArea.active == true){
          this.scientificAreas.push(scientificArea);
        }
      }
    });
  }

  saveUser(){
    if(this.formCreateEditUser.valid){
      this.formCreateEditProfessorAndStudent.value.user = this.formCreateEditUser.value; // set user from form
      this.formCreateEditProfessorAndStudent.value.address = this.formCreateEditAddress.value; // set address object form form

      if(this.formCreateEditUser.value["id"] == null){
        this.userService.create(this.formCreateEditUser.value).subscribe(data => {
         
          if(this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_PROFESSOR")) {
              this.professorService.create(this.formCreateEditProfessorAndStudent.value).subscribe(x => {
                //Create every rank of list
                for(let newRank of this.newListRanks) { this.rankService.create(newRank).subscribe(); }
              });
            } 

          if(this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_STUDENT")) { 
            this.studentService.create(this.formCreateEditProfessorAndStudent.value).subscribe();  
          }
            this.isSuccessful = true;
            this.isSignUpFailed = false;
            this.cancel(); //after save user
          },
          err => {
            this.errorMessage = err.error.message;
            this.isSignUpFailed = true;
          }
        );
      } else {
        this.userService.update(this.formCreateEditUser.value.id, this.formCreateEditUser.value).subscribe(
          data => {
            if (((this.firstRole.some((role:any) => role.name === "ROLE_PROFESSOR")) && 
              (this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_PROFESSOR")))) {
              this.professorService.update(this.formCreateEditUser.value.id, this.formCreateEditProfessorAndStudent.value).subscribe(); // ako je bio profesor i ostao profesor radimo update
            } else if (((!this.firstRole.some((role:any) => role.name == "ROLE_PROFESSOR")) && 
                      (this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_PROFESSOR")))) { 
              this.professorService.create(this.formCreateEditProfessorAndStudent.value).subscribe(); // ako nije bio profesor i postao je profesor radi se create
            } else if (((this.firstRole.some((role:any) => role.name === "ROLE_PROFESSOR")) && 
                      (!this.formCreateEditUser.value.roles.some((role:any) => role.name == "ROLE_PROFESSOR")))) {
              this.professorService.delete(this.formCreateEditUser.value.id).subscribe(); // ako je bio profesor i vise nije profesor radi se delete
            }

            if (((this.firstRole.some((role:any) => role.name === "ROLE_STUDENT")) && 
            (this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_STUDENT")))) {
              this.studentService.update(this.formCreateEditUser.value.id, this.formCreateEditProfessorAndStudent.value).subscribe(); // ako je bio student i ostao student radi se update
            } else if (((!this.firstRole.some((role:any) => role.name === "ROLE_STUDENT")) && 
                      (this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_STUDENT")))){ // ako nije bio student i postao je student radi se create
              this.studentService.create(this.formCreateEditProfessorAndStudent.value).subscribe(); 
            } else if (((this.firstRole.some((role:any) => role.name === "ROLE_STUDENT")) && 
                      (!this.formCreateEditUser.value.roles.some((role:any) => role.name === "ROLE_STUDENT")))){ // ako je bio student a vise nije radi se delete
              this.studentService.delete(this.formCreateEditUser.value.id).subscribe();
            }

            this.isSuccessfulEdit = true;
            this.isEditFailed = false;
            this.cancel(); //after save user
          },
          err => {
            this.errorMessage = err.error.message;
            this.isEditFailed = true;
          }
        );
      }
    }
  };

  cancel(){
    this.formCreateEditProfessorAndStudent.reset();
    this.formCreateEditUser.get("id")?.setValue(null);
    this.formCreateEditUser.reset();
    this.location.back();
  }

  
  cancelCreateEditRanks(){
    this.formCreateEditRanks.reset(); 
    this.userEditRankInList = false; 
    this.userEditRankInListID = null;
    this.isCreateRankFailed = false;
  }

  checkJMBG(){
    if(this.formCreateEditProfessorAndStudent.get("jmbg").valid == true){
      this.professorService.checkJmbg(this.formCreateEditProfessorAndStudent.value.jmbg, this.formCreateEditProfessorAndStudent.value.id).subscribe(data => {
        this.isSignUpFailed = false;
        this.checkJmbg = true;
        this.formCreateEditProfessorAndStudent.controls.jmbg.setErrors(null);
      }, err => {
        this.formCreateEditProfessorAndStudent.controls.jmbg.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        this.checkJmbg = false;
      });
      this.studentService.checkJmbg(this.formCreateEditProfessorAndStudent.value.jmbg, this.formCreateEditProfessorAndStudent.value.id).subscribe(data => {
        this.isSignUpFailed = false;
        this.checkJmbg = true;
        this.formCreateEditProfessorAndStudent.controls.jmbg.setErrors(null);
      }, err => {
        this.formCreateEditProfessorAndStudent.controls.jmbg.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        this.checkJmbg = false;
      });
    }
  }

  checkUserEmail(){
    if(this.formCreateEditUser.get("email").valid == true){
      this.userService.checkEmail(this.formCreateEditUser.value.email, this.formCreateEditUser.value.id).subscribe(data => {
        this.isSignUpFailed = false;
        this.checkUser = true;
        this.formCreateEditUser.controls.email.setErrors(null);
      }, err => {
        this.formCreateEditUser.controls.email.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        this.checkUser = false;
      });
    }
  }

  checkUserUsername(){
    if(this.formCreateEditUser.get("username").valid == true){
      this.userService.checkUsername(this.formCreateEditUser.value.username, this.formCreateEditUser.value.id).subscribe(data => {
        this.isSignUpFailed = false;
        this.checkUser = true;
        this.formCreateEditUser.controls.username.setErrors(null);
      }, err => {
        this.formCreateEditUser.controls.username.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        this.checkUser = false;
      });
    }
  }

  selectCountry(selectedCountry: string){
    for(let country of Country.getAllCountries()){  // get all country and city for that country
      if(country.name == selectedCountry) {
        this.citys = [];
        for(let city of City.getCitiesOfCountry(country.isoCode)){ this.citys.push(city.name); };
      }
    };
  }

  saveRank(){
    this.formCreateEditRanks.value.professor = this.formCreateEditProfessorAndStudent.value; // set professor from form professor
    this.formCreateEditRanks.value.professor.user = this.formCreateEditUser.value; // set user from form
    try {   
      if(this.formCreateEditRanks.value.typeRanks != null && this.formCreateEditRanks.value.scientificArea != null &&
        this.formCreateEditRanks.value.electionDate != null && this.formCreateEditRanks.value.terminationDate != null) {
        if (!this.newListRanks.some((rank: Rank) => (rank.scientificArea.name == this.formCreateEditRanks.value.scientificArea.name) &&
          (this.newListRanks[this.userEditRankInListID].scientificArea.name != rank.scientificArea.name))) { //IF EXIST RANK FOR THAT SCIENTIFIC AREA
          if(this.formCreateEditProfessorAndStudent.value.id == null){ // IF FISRT TIME CREATE PROFESSOR AND CREATE RANKS
            if(this.userEditRankInList == true){
              this.newListRanks[this.userEditRankInListID] = this.formCreateEditRanks.value;
              this.userEditRankInList = false;
              this.userEditRankInListID = null;
            } 
            else { 
              this.newListRanks.push(this.formCreateEditRanks.value); 
            }
            this.dataSource = new MatTableDataSource(this.newListRanks); 
          } 
          else {
            let userUsernameInput = this.formCreateEditUser.value.username;
            this.formCreateEditRanks.value.professor.user.username = JSON.parse(this.route.snapshot.paramMap.get('userForChange'))['username']; // i set first username before change username if have exist been
            if(this.formCreateEditRanks.value.id != null){ // IF PROFESSOR EXIST AND HAVE RANKS MAKE UPDATE
              this.rankService.update(this.formCreateEditRanks.value.id, this.formCreateEditRanks.value).subscribe(x => { this.refreshRanksTable(); });
            }
            else { // IF PROFESSOR EXIST BUT DON'T HAVE RANKS ADD NEW RANKS NOW
              this.rankService.create(this.formCreateEditRanks.value).subscribe(x => { this.refreshRanksTable(); });
            }
            this.formCreateEditRanks.value.professor.user.username = userUsernameInput;
            this.userEditRankInListID = null;
          }
          this.formCreateEditRanks.reset(); 
          this.accordion.closeAll();
          this.isCreateRankFailed = false;
        } else {
          this.errorMessage = "Ranks have already been awarded for this scientific area!"
          this.isCreateRankFailed = true;
        }
      } else {
        this.errorMessage = "You must fill in all the fields!"
        this.isCreateRankFailed = true;
      }
    } catch (err){
      this.errorMessage = "Ranks have already been awarded for this scientific area!"
      this.isCreateRankFailed = true;
    }
  }

  editRank(id: number, rank: Rank){
    this.userEditRankInListID = id;
    this.formCreateEditRanks.patchValue(rank);
  }

  editRankInList(id: number, rank: Rank){
    this.userEditRankInList = true;
    this.userEditRankInListID = id;
    this.formCreateEditRanks.patchValue(rank);
  }

  deleteRank(id: number) {
    if(this.formCreateEditProfessorAndStudent.value.id != null){
      this.rankService.delete(id).subscribe(x => { this.refreshRanksTable(); });
    } else {
      this.newListRanks.splice(id, 1);
      this.dataSource = new MatTableDataSource(this.newListRanks); 
    }
  };

  refreshRanksTable(){
    this.rankService.getByUsername(this.formCreateEditUser.value.username).subscribe((rank: Rank[]) => { 
      this.dataSource = new MatTableDataSource(rank); 
      this.newListRanks = rank;
    });
  }

  comparator(v1: any, v2: any) {
    if(v1 && v2)
    {
      return v1["id"] == v2["id"]
    } else {
      return  v1 == v2
    }
  }

  refreshCheckRolesProfessorAndStudent(roleName : []){
    this.checkRolesExistProfessorsOrStudentsBoolean = false;
    this.checkRolesIsStudent = false;
    if(roleName != null) { // ovo nam treba da bi pokazali ostale korake za editovanje, kreiranje
      if((roleName.some((role:any) => role.name === "ROLE_STUDENT")) || 
        (roleName.some((role:any) => role.name === "ROLE_PROFESSOR"))){
        this.checkRolesExistProfessorsOrStudentsBoolean = true;
        if((roleName.some((role:any) => role.name === "ROLE_STUDENT")) && (!roleName.some((role:any) => role.name === "ROLE_PROFESSOR"))){
          this.checkRolesIsStudent = true;
        }
      }
    }
  }
  

}
