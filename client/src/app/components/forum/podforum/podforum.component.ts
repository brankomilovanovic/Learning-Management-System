import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { Podforum } from 'src/app/model/forum/podforum';
import { PodforumService } from 'src/app/service/forum/podforum/podforum.service';
import { ObjavaService } from 'src/app/service/forum/objava/objava.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Tema } from 'src/app/model/forum/tema';
import { Observable } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { KorisnikNaForumuService } from 'src/app/service/forum/korisnik-na-forumu/korisnik-na-forumu.service';
import { UserService } from 'src/app/service/user/user.service';
import { KorisnikNaForumu } from 'src/app/model/forum/korisnik-na-forumu';
import { ClearFormat } from '@syncfusion/ej2-angular-richtexteditor';
import { TemaService } from 'src/app/service/forum/tema/tema.service';

@Component({
  selector: 'app-podforum',
  templateUrl: './podforum.component.html',
  styleUrls: ['./podforum.component.css']
})
export class PodforumComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  obs: Observable<any>;
  dataSource: MatTableDataSource<Tema>;
  
  podforum : Podforum = null;
  role : String = null;

  username : string = null;
  korisnikNaForumu : KorisnikNaForumu = Object.create({ id: null, vremePrijavljivanja: null, objave: 0, user: null, follow_teme: [], follow_podforum: [] });
  pratiOvajPodforum : boolean = false;

  totalTema : number = 0;
  
  constructor(private tokenStorageService: TokenStorageService, private podforumService : PodforumService, private korisnikNaForumuService: KorisnikNaForumuService,
    private objavaService: ObjavaService, private activatedRoute: ActivatedRoute, private changeDetectorRef: ChangeDetectorRef, private userService: UserService,
    private temaService: TemaService) { }

  ngOnInit(): void {
    if(!!this.tokenStorageService.getUser()) {
      const user = this.tokenStorageService.getUser();
      if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
      else if(user.roles.includes('ROLE_PROFESSOR')) { this.role = "ROLE_PROFESSOR"; }
      else if(user.roles.includes('ROLE_STUDENT')) { this.role = "ROLE_STUDENT"; }
      else if(user.roles.includes('ROLE_USER')) { this.role = "ROLE_USER"; }
      this.username = user.username;
    }
    this.refreshPodforum();
  }

  refreshPodforum(){
    let korisnikNaForumuID = -1;
    if(this.username != null) {
      this.korisnikNaForumuService.getByUsername(this.username).subscribe(korisnikNaForumu => { 
        this.korisnikNaForumu = korisnikNaForumu;
        korisnikNaForumuID = korisnikNaForumu.id;
      }, err => {
        this.userService.getOne(this.username).subscribe(korisnik => { this.korisnikNaForumu.user = korisnik; });
      });
    }
    this.podforumService.getOne(this.activatedRoute.snapshot.queryParams.id).subscribe(podforum => { 
      
      this.korisnikNaForumuService.getFollowPodforum(podforum.id, korisnikNaForumuID).subscribe(x => { 
        this.pratiOvajPodforum = Boolean(x); 
      }, err => {
        this.userService.getOne(this.username).subscribe(korisnik => { this.korisnikNaForumu.user = korisnik; });
      }); // dobavljamo true ili false da li vec prati ovu temu

      this.temaService.getAllByPodforum(podforum.id).subscribe(teme => { 
        for(let tema of teme){
          this.objavaService.getFirstObjavaForTopic(tema.id).subscribe(objava => { tema.prvaObjava = objava; }); // Dodajemo prvu objavu za ovu temu, on je kreator
          this.objavaService.getLastPostForTopic(tema.id).subscribe(objava => { tema.zadnjaObjava = objava; });
          let brObjava = 0;
          for(let i of tema.objava) { brObjava ++; }
          tema.brojObjava = brObjava;
          this.totalTema ++;
        }
        this.dataSource = new MatTableDataSource(teme);
        this.changeDetectorRef.detectChanges();
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.obs = this.dataSource.connect();
      })
      this.podforum = podforum;
  });
  }

  addNotification(podforum: Podforum) {
    //Prvo proveravamo da li ima korisnika vec na forumu ako nema pravimo novog, ako ga ima radimo samo update objava + 1
    if(this.korisnikNaForumu.id == null) { 
      var trenutno_vreme = new Date();
      trenutno_vreme.setHours(trenutno_vreme.getHours() + 2);
      this.korisnikNaForumu.vremePrijavljivanja = trenutno_vreme; 
      this.korisnikNaForumu.follow_podforum = [podforum];
      this.korisnikNaForumuService.create(this.korisnikNaForumu).subscribe(x => { this.pratiOvajPodforum = true; this.refreshPodforum(); });
    } else {  
      this.korisnikNaForumu.follow_podforum.push(podforum);
      this.korisnikNaForumuService.update(this.korisnikNaForumu.id, this.korisnikNaForumu).subscribe(x => {  this.pratiOvajPodforum = true; this.refreshPodforum(); });
    }
  }

  removeNotification(tema: Tema) {
    this.korisnikNaForumu.follow_podforum.forEach((value, index) => { if(value['id'] == tema['id']) this.korisnikNaForumu.follow_podforum.splice(index,1); }); //Izbacujemo iz liste notifikaciju koju smo otvorili
    this.korisnikNaForumuService.update(this.korisnikNaForumu.id, this.korisnikNaForumu).subscribe(x => {  this.pratiOvajPodforum = false; this.refreshPodforum(); });
  }
}
