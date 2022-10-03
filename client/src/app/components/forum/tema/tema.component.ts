import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Objava } from 'src/app/model/forum/objava';
import { Tema } from 'src/app/model/forum/tema';
import { ObjavaService } from 'src/app/service/forum/objava/objava.service';
import { TemaService } from 'src/app/service/forum/tema/tema.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { UserService } from 'src/app/service/user/user.service';
import { Location } from '@angular/common';
import { KorisnikNaForumu } from 'src/app/model/forum/korisnik-na-forumu';
import { KorisnikNaForumuService } from 'src/app/service/forum/korisnik-na-forumu/korisnik-na-forumu.service';
import { MailService } from 'src/app/service/mail/mail.service';
import { FileService } from 'src/app/service/file/file.service';

@Component({
  selector: 'app-tema',
  templateUrl: './tema.component.html',
  styleUrls: ['./tema.component.css']
})
export class TemaComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  obs: Observable<any>;
  dataSource: MatTableDataSource<Objava>;
  
  formCreateEditObjava : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "vremePostavljanja" : new FormControl(null, [Validators.required]),
    "sadrzaj" : new FormControl(null, [Validators.required]),
    "tema" : new FormControl(null, [Validators.required]),
    "prilozi" : new FormArray([]),
    "autor" : new FormControl(null, [Validators.required])
  });

  tema : Tema = null;
  role : String = null;
  korisnikNaForumu : KorisnikNaForumu = Object.create({ id: null, vremePrijavljivanja: null, objave: 0, user: null, follow_teme: [], follow_podforum: [] });
  prvaObjavaID : number = 0;

  temaID : number = 0; 
  username : string = null;

  pratiOvuTemu : boolean = false;

  constructor(private activatedRoute: ActivatedRoute, private temaService: TemaService, private objavaService: ObjavaService, private tokenStorageService: TokenStorageService,
    private userService: UserService, private changeDetectorRef: ChangeDetectorRef, private location: Location, private korisnikNaForumuService: KorisnikNaForumuService,
    private mailService: MailService, private fb:FormBuilder, private fileService: FileService) { }

  ngOnInit(): void {
    if(!!this.tokenStorageService.getUser()) {
      const user = this.tokenStorageService.getUser();
      if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
      else if(user.roles.includes('ROLE_PROFESSOR')) { this.role = "ROLE_PROFESSOR"; }
      else if(user.roles.includes('ROLE_STUDENT')) { this.role = "ROLE_STUDENT"; }
      else if(user.roles.includes('ROLE_USER')) { this.role = "ROLE_USER"; }
      this.username = user.username;
    }

    this.temaID = this.activatedRoute.snapshot.queryParams.id;
    this.refreshTemu();
  }

  refreshTemu(){
    let korisnikNaForumuID : number = -1;
    if(this.username != null) {
      this.korisnikNaForumuService.getByUsername(this.username).subscribe(korisnikNaForumu => { 
        this.korisnikNaForumu = korisnikNaForumu;
        korisnikNaForumuID = korisnikNaForumu.id;
      }, err => {
        this.userService.getOne(this.username).subscribe(korisnik => { this.korisnikNaForumu.user = korisnik; });
      });
    }

    this.temaService.getOne(this.temaID).subscribe(tema => {  
      if(this.role != null) { 
        this.korisnikNaForumuService.getFollowTema(tema.id, korisnikNaForumuID).subscribe(x => { 
          this.pratiOvuTemu = Boolean(x); 
        }, err => {
          this.userService.getOne(this.username).subscribe(korisnik => { this.korisnikNaForumu.user = korisnik; });
        }); // dobavljamo true ili false da li vec prati ovu temu
      }
      this.tema = tema;
      this.formCreateEditObjava.get('tema').setValue(tema);
      this.temaService.dodajPregledTemi(tema.id).subscribe();
      this.formCreateEditObjava.get("id")?.setValue(null);
      this.formCreateEditObjava.get("sadrzaj")?.setValue(null);

      this.objavaService.getAllForTema(tema.id).subscribe(objave => {
        this.dataSource = new MatTableDataSource(objave);
        this.changeDetectorRef.detectChanges();
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        this.prvaObjavaID = objave[0].id;
      });
     });
     this.addField();
  }

  // postoji problem kada sacuvam objavu ne obrisu se polja!!! treba da se resetuje

  save(){
    if(this.formCreateEditObjava.value.sadrzaj != null) {
      //Prvo proveravamo da li ima korisnika vec na forumu ako nema pravimo novog, ako ga ima radimo samo update objava + 1
      this.korisnikNaForumu.objave += 1;
      if(this.korisnikNaForumu.id == null) { 
        var trenutno_vreme = new Date();
        trenutno_vreme.setHours(trenutno_vreme.getHours() + 2);

        this.korisnikNaForumu.vremePrijavljivanja = trenutno_vreme; 
        this.korisnikNaForumuService.create(this.korisnikNaForumu).subscribe(korisnikNaForumu => { this.saveObjava(korisnikNaForumu); });
      } else {
        this.korisnikNaForumuService.update(this.korisnikNaForumu.id, this.korisnikNaForumu).subscribe(korisnikNaForumu => { this.saveObjava(korisnikNaForumu); });
      }
    }
  }

  saveObjava(korisnikNaForumu) {
    this.formCreateEditObjava.get('autor').setValue(korisnikNaForumu);
    if(this.formCreateEditObjava.value.id == null){
      var trenutno_vreme = new Date();
      trenutno_vreme.setHours(trenutno_vreme.getHours() + 2);
      this.formCreateEditObjava.get('vremePostavljanja').setValue(trenutno_vreme);
      this.objavaService.create(this.formCreateEditObjava.value).subscribe(objava => { 
        for(let file of this.formCreateEditObjava.value.prilozi) { // Kreiramo file sa referencom na objavu
          if(file.opis != '' && file.url != '') {
            file.objava = objava;
            this.fileService.create(file).subscribe();
          }
        };
        this.prilozi.clear();
        this.refreshTemu(); 
      });
    } else {
      this.objavaService.update(this.formCreateEditObjava.value.id, this.formCreateEditObjava.value).subscribe(objava => { 
        for(let file of this.formCreateEditObjava.value.prilozi) { // Kreiramo file sa referencom na objavu
          if(file.opis != '' && file.url != '') {
            file.objava = objava;
            this.fileService.create(file).subscribe();
          }
        };
        this.prilozi.clear();
        this.refreshTemu(); 
      });
    }

    //Saljemo mail svim korisnicima koji su pretplaceni na podforum
    this.korisnikNaForumuService.getAllFollowTema(this.tema.id).subscribe(korisniciNaForumu => { 
      for(let korisnik of korisniciNaForumu) {
        this.mailService.sendEmail(korisnik.user.email, "Upravo je postavljena nova objava u temi koji pratite (" + this.tema.naziv + ")", 
          this.formCreateEditObjava.value.autor.user.name + " " + this.formCreateEditObjava.value.autor.user.surname + " je upravo postavio novu objavu u temi koji pratite.").subscribe();
      }
    });
  }

  delete(id: number, tema_id: number){
    if(id == this.prvaObjavaID){
      this.temaService.delete(tema_id).subscribe(x => { this.cancel(); });
    } else {
      this.objavaService.delete(id).subscribe(x => { this.prilozi.clear(); this.refreshTemu(); });
    }
  }

  deleteTema(tema_id: number){
    this.temaService.delete(tema_id).subscribe(x => { this.cancel(); });
  }

  addNotification(tema: Tema) {
    //Prvo proveravamo da li ima korisnika vec na forumu ako nema pravimo novog, ako ga ima radimo samo update objava + 1
    if(this.korisnikNaForumu.id == null) { 
      var trenutno_vreme = new Date();
      trenutno_vreme.setHours(trenutno_vreme.getHours() + 2);
      this.korisnikNaForumu.vremePrijavljivanja = trenutno_vreme; 
      this.korisnikNaForumu.follow_teme = [tema];
      this.korisnikNaForumuService.create(this.korisnikNaForumu).subscribe(x => { this.pratiOvuTemu = true; this.refreshTemu(); });
    } else {  
      this.korisnikNaForumu.follow_teme.push(tema);
      this.korisnikNaForumuService.update(this.korisnikNaForumu.id, this.korisnikNaForumu).subscribe(x => { this.pratiOvuTemu = true; this.refreshTemu(); });
    }
  }

  removeNotification(tema: Tema) {
    this.korisnikNaForumu.follow_teme.forEach((value, index) => { if(value['id'] == tema['id']) this.korisnikNaForumu.follow_teme.splice(index,1); }); //Izbacujemo iz liste notifikaciju koju smo otvorili
    this.korisnikNaForumuService.update(this.korisnikNaForumu.id, this.korisnikNaForumu).subscribe(x => { this.pratiOvuTemu = false; this.refreshTemu(); });
  }

  edit(objava: Objava){
    window.scrollTo(0, document.body.scrollHeight);
    this.formCreateEditObjava.patchValue(objava);

    let index : number = 0;
      for(let i of objava.prilozi) {
        if(index > 0){ this.addField(); } //Pravi novo polje za setovanje autora
        this.prilozi.at(index).patchValue(i) //Setuje autora u novo polje koje je napravljeno
        index ++;      
      }
  }

  cancel(){
    this.prilozi.clear();
    this.formCreateEditObjava.get("id")?.setValue(null);
    this.formCreateEditObjava.reset();
    this.location.back();
  }

  reset(){
    this.formCreateEditObjava.get('id')?.setValue(null); 
    this.formCreateEditObjava.get('sadrzaj')?.setValue(null); 
    this.prilozi.clear(); 
    this.addField();
  }

  get prilozi() {
    return this.formCreateEditObjava.controls["prilozi"] as FormArray;
  }

  addField() {
    const prilog = this.fb.group({
      id: [null],
      opis: ['', Validators.required],
      url: ['', Validators.required]
    });
    this.prilozi.push(prilog);
  }

  deleteField(index: number, prilog_id: number) {
    if(prilog_id != null) {
      this.fileService.delete(prilog_id).subscribe();
    }
    if (this.prilozi.length !== 1) {
      this.prilozi.removeAt(index);
    }
  }
}
