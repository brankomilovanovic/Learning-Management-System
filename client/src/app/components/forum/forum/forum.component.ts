import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Objava } from 'src/app/model/forum/objava';
import { Podforum } from 'src/app/model/forum/podforum';
import { ForumService } from 'src/app/service/forum/forum/forum.service';
import { ObjavaService } from 'src/app/service/forum/objava/objava.service';
import { PodforumService } from 'src/app/service/forum/podforum/podforum.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})
export class ForumComponent implements OnInit {

  role : String = null;
  podforumi : Podforum[] = [];

  totalPodforum : number = 0;
  totalTema : number = 0;
  totalObjava : number = 0;
  totalKorisnika : number = 0;
  lastPost : Objava[] = [];
  nemajuLastPost : number[] = [];

  constructor(private tokenStorageService: TokenStorageService, private podforumService : PodforumService, private userService: UserService,
    private objavaService: ObjavaService, private fourmService: ForumService, private router: Router) { }

  ngOnInit(): void {
    this.fourmService.getForum().subscribe(x => {
      if(!!this.tokenStorageService.getUser()) {
        const user = this.tokenStorageService.getUser();
        if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
        else if(user.roles.includes('ROLE_PROFESSOR')) { this.role = "ROLE_PROFESSOR"; }
        else if(user.roles.includes('ROLE_STUDENT')) { this.role = "ROLE_STUDENT"; }
        else if(user.roles.includes('ROLE_USER')) { this.role = "ROLE_USER"; }
      }
      if(x.javni == false && this.role != "ROLE_ADMINISTRATOR" && this.role != "ROLE_PROFESSOR") { // ako je forum javni dozvoliti pristup ako nije zabraniti 
        this.router.navigate(['/home']);
        console.log("Forum je trenutno privatan!")
      } else {
        this.podforumService.getAll().subscribe((podforumi: Podforum[]) => { 
          this.podforumi = podforumi; 
          this.totalPodforum = podforumi.length; 
          for(let podforum of podforumi) { 
            this.totalTema += podforum.totalTema; 
            this.totalObjava += podforum.totalObjava; 
            this.objavaService.getLastPostForSubforum(podforum.id).subscribe(objava => { this.lastPost.push(objava); }, err => { this.nemajuLastPost.push(podforum.id); });
          };
          this.userService.countUser("", "", "").subscribe((x : number) => { this.totalKorisnika = x }); 
        });
      }
    });
  }
}
