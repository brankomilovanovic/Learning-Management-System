import { Component, OnInit } from '@angular/core';
import { University } from 'src/app/model/university';
import { StudentServiceService } from 'src/app/service/student-service/student-service.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { UserService } from 'src/app/service/user/user.service';
import { StudentService } from 'src/app/model/student-service';
import { FormControl, FormGroup } from '@angular/forms';
import { Subject } from 'src/app/model/subject';
import { FollowService } from 'src/app/service/follow-sub/follow.service';
import { followSub } from 'src/app/model/followSub';
import { Topic } from 'src/app/model/topic';
import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y';
import { User } from 'src/app/model/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {

  content: string;
  currentUser: any;
  uni : University []=[];
  username: string =  null;
  subjects: StudentService[] = [];
  s: Subject[]= [];
  topic: Topic[]=[];
  tema = false;
  user: User[]=[];

  constructor(private tokenStorageService: TokenStorageService, private userService: UserService, private token: TokenStorageService
    , private sub: StudentServiceService, private follow: FollowService, public us: UserService, private router: Router) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    
    if(user.roles.includes('ROLE_STUDENT')){
      this.username = user.username;
      
      

      this.us.getOne(this.username).subscribe((user : User) => {
        console.log(user)
      this.sub.getSubjects(this.username).subscribe((value : StudentService[])=>{
        this.subjects = value
          //console.log(value)

          for(let p of value){
            if(p.choosed == false){
              this.openNav();
              //console.log(p)
            }
          }

      });
      //DOBAVLJANJE PREDMETA KOJE JE STUDENT IZABRAO
      this.follow.getSubjects(user.id).subscribe((v : followSub[])=>{
        console.log(v)
        for(let p of v){
          for(let predmet of p.subjects){
              this.s.push(predmet);
          }

          console.log("PREDMETI KOJE STUDENT PRATI: ", this.s)
        }
      });
    })
    
  }
  }
  about(subject: Subject){
    this.router.navigate(['user/subjectdetails', {subforchange: JSON.stringify(subject)}]);
  }
  openNav() {
      document.getElementById("myNav").style.height = "100%"; 
  }
  
  closeNav() {
    document.getElementById("myNav").style.height = "0%";
  }


}