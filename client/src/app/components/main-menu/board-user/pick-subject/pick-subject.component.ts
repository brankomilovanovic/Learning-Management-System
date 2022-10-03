import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { StudentService } from 'src/app/model/student-service';
import { Subject } from 'src/app/model/subject';
import { StudentServiceService } from 'src/app/service/student-service/student-service.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { UserService } from 'src/app/service/user/user.service';
import { NgxNotificationMsgService, NgxNotificationStatusMsg } from 'ngx-notification-msg';
import { FollowService } from 'src/app/service/follow-sub/follow.service';
import { followSub } from 'src/app/model/followSub';
import { SelectionModel } from '@angular/cdk/collections';
import { ThrowStmt } from '@angular/compiler';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { SubjectRealization } from 'src/app/model/subject-realization';
import { TeacherOnRealizationService } from 'src/app/service/teacher-on-realization/teacher-on-realization.service';
import { TeacherOnRealization } from 'src/app/model/teacher-on-realization';

@Component({
  selector: 'app-pick-subject',
  templateUrl: './pick-subject.component.html',
  styleUrls: ['./pick-subject.component.css', './p.css']
})
export class PickSubjectComponent implements OnInit {

  username: string =  null;
  subjects: StudentService[] = [];
  s: Subject[]=[];
  odabran: Subject[]=[];
  predmeti: followSub[]=[];
  kliknut = false;
  modal=false;
  espbCounter : number = 0;
  selection = new SelectionModel <StudentService> (true, []);
  userS: User;
  realizations: TeacherOnRealization[]=[];
  realization: Subject[]=[];

  formCreateEditStudent : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "user" : new FormControl(null),
    "subjects" : new FormControl(null)
  });
  
  constructor(private tokenStorageService: TokenStorageService, private userService: UserService, private token: TokenStorageService
    , private sub: StudentServiceService, private readonly ngxNotificationMsgService: NgxNotificationMsgService
    , private follow: FollowService, private router: Router, private teacherOnRealizationService: TeacherOnRealizationService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();

    if(user.roles.includes('ROLE_STUDENT')){
      this.username = user.username;

      this.userService.getOne(this.username).subscribe((user : User) => {
        this.userS = user
      });

      this.follow.getAll().subscribe((value: followSub[])=>{
          this.predmeti = value
          //console.log(value)
      });
      
      this.teacherOnRealizationService.getAll().subscribe(objects => {
        this.realizations = objects;
        console.log(objects);

        this.sub.getSubjects(this.username).subscribe((value : StudentService[])=>{
          this.subjects = value
          console.log("SVE REALIZACIJE: ", value)
          //AKO JE TAJ PREDMET U TOJ REALIZACIJI POSTAVI TRUE NA TU REALIZACIJU
          for(let y of this.subjects){
            if(y.choosed == false){
            for(let r of y.year){
              for(let p of r.subjects){

                for(let r of objects){
                  for(let t of r.subjectRealization){
                    if(p.id == t.subject.id){
                      this.s.push(p);
                      console.log("UBACENI PREDMETI ZA KOJE POSTOJI REALIZACIJA: ", this.s)
                    }
                  }
                }

                //this.s.push(p);
              }
            }
          }
          }

        });
        
        // for(let r of objects){
        //   for(let t of r.subjectRealization){
        //     if()
        //   }
        // }

      });

      // this.sub.getSubjects(this.username).subscribe((value : StudentService[])=>{
      //   this.subjects = value
      //   console.log("SVE REALIZACIJE: ", value)
      //   //AKO JE TAJ PREDMET U TOJ REALIZACIJI POSTAVI TRUE NA TU REALIZACIJU
      //   for(let y of this.subjects){
      //     if(y.choosed == false){
      //     for(let r of y.year){
      //       for(let p of r.subjects){
      //         this.s.push(p);
      //       }
      //     }
      //   }
      //   }

      // });
    }

    this.myFunction();
  }

  send(){
    const user = this.tokenStorageService.getUser();
      this.formCreateEditStudent.value.user = this.userS
      this.formCreateEditStudent.value.subjects = this.odabran
        if(this.odabran.length > 0){
          for(let y of this.subjects){
            for(let r of y.year){
              for(let p of r.subjects){
                for(let o of this.odabran){
                  if(p.id == o.id){
                    console.log("postavi na true u bazi za", y.id)
                    y.choosed = true;
                    this.sub.update(y.id, y).subscribe(e =>{console.log("USPESNO SETOVAN TRUE ZA TU REALIZACIJU", y.id)})
                  }
                }
              }
            }
          }
          /*return(year: Year) {
            year.active = true;
            this.ys.update(year.id, year).subscribe(rank => { this.refreshTable(); });
          };*/
          this.follow.create(this.formCreateEditStudent.value).subscribe(sub=> {console.log("Uspesno dodato u bazu")
          this.formCreateEditStudent.get("id")?.setValue(null);
          this.formCreateEditStudent.reset();
          this.ngxNotificationMsgService.open({
            status: NgxNotificationStatusMsg.SUCCESS,
            header: 'Choosed subject',
            messages: ['You have succesfully choosed subjects for following']
          });
          this.router.navigate(['user']);
        });
        }else{
        this.ngxNotificationMsgService.open({
          status: NgxNotificationStatusMsg.SUCCESS,
          header: 'Pick a subject',
          messages: ['You have to pick a subject first!']
        });
        }
        this.odabran = []
        this.kliknut = false
  }

  confirmFollowing(p: StudentService){
    p.choosed = true;
    this.sub.update(p.id, p).subscribe(s=>{});
  }

  pick(id:Subject){
   //for(let s of this.s){
    //if(s.id == id.id){
      this.odabran.push(id);
      this.espbCounter += id.espb;
      //console.log("ODABRANI ZA LISTU PREDMETA KOJI IDU U BAZU", this.odabran)
      this.kliknut = true;
      this.s.forEach((el, index)=>{
        if(el==id) this.s.splice(index, 1)
      });
      //console.log("IZBACENI IZ GLAVNE LISTE", this.s)
    //}
   //}
   
  }

  delete(id: Subject){
    this.odabran.forEach((el, index)=>{
      if(el==id) this.odabran.splice(index, 1)
    });
    this.s.push(id)
    this.espbCounter -= id.espb;
    if(this.odabran.length == 0){
      this.kliknut = false
   }
  }

  myFunction() {
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 7000);
  }

}
