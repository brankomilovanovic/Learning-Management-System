import { global } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'src/app/model/subject';

import { SubjectRealization } from 'src/app/model/subject-realization';
import { TypeOfTeaching } from 'src/app/model/type-of-teaching';
import { SubjectRealizationService } from 'src/app/service/subject-realization/subject-realization.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-moderator.component.html',
  styleUrls: ['./board-moderator.component.css']
})
export class BoardModeratorComponent implements OnInit {

  content: string;

  username: string =  null;
  subjects: SubjectRealization[] = [];
  typeteaching: TypeOfTeaching[]=[];
  subjectNo: Subject;
  odabran: Subject;

  kliknut: boolean = false;

  constructor(private tokenStorageService: TokenStorageService, private subjectService: SubjectRealizationService, private router: Router) { }

  currentUser: any;

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();

    if(user.roles.includes('ROLE_PROFESSOR')){
      this.username = user.username;

      this.subjectService.getByUsername(this.username).subscribe((value : SubjectRealization[])=>{
        this.subjects = value;
        console.log(value)
      });
    }

  }

  about(subject: Subject){
    this.router.navigate(['professor/aboutsubject', {subforchange: JSON.stringify(subject)}]);
  }

  details(s: Subject){
    console.log("ODABRAN PREDMET: ", s)
    this.odabran = s;
    this.kliknut = true;
  }

}
