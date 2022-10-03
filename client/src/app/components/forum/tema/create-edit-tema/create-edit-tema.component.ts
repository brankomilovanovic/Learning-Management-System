import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Podforum } from 'src/app/model/forum/podforum';
import { ObjavaService } from 'src/app/service/forum/objava/objava.service';
import { KorisnikNaForumuService } from 'src/app/service/forum/korisnik-na-forumu/korisnik-na-forumu.service';
import { PodforumService } from 'src/app/service/forum/podforum/podforum.service';
import { MailService } from 'src/app/service/mail/mail.service';
import { TemaService } from 'src/app/service/forum/tema/tema.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { UserService } from 'src/app/service/user/user.service';
import { Location } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FileService } from 'src/app/service/file/file.service';
import { TABLE_HORIZONTAL_SPLIT } from '@syncfusion/ej2-angular-richtexteditor';

@Component({
  selector: 'app-create-edit-tema',
  templateUrl: './create-edit-tema.component.html',
  styleUrls: ['./create-edit-tema.component.css']
})
export class CreateEditTemaComponent implements OnInit {

  podforum : Podforum = null;

  formCreateEditTema : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "naziv" : new FormControl(null, [Validators.required]),
    "pregleda" : new FormControl(0),
    "podforum" : new FormControl(null, [Validators.required]),
    "autor" : new FormControl(null, [Validators.required])
  });

  formCreateEditObjava : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "vremePostavljanja" : new FormControl(null, [Validators.required]),
    "sadrzaj" : new FormControl(null, [Validators.required]),
    "tema" : new FormControl(null),
    "prilozi" : new FormArray([]),
    "autor" : new FormControl(null, [Validators.required])
  });

  formAutor : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "vremePrijavljivanja" : new FormControl(null, [Validators.required]),
    "objave" : new FormControl(0),
    "user" : new FormControl(null, [Validators.required])
  });

  constructor(private tokenStorageService: TokenStorageService, private podforumService : PodforumService, private activatedRoute: ActivatedRoute, private userService: UserService,
    private location: Location, private korisnikNaForumuService: KorisnikNaForumuService, private http: HttpClient, private objavaService: ObjavaService, private temaService: TemaService,
    private mailService: MailService, private fb:FormBuilder, private fileService: FileService) { }

  ngOnInit(): void {
    //Dodeljujemo odmah korisnika i podforum
    if(!!this.tokenStorageService.getUser()) { 
      this.korisnikNaForumuService.getByUsername(this.tokenStorageService.getUser().username).subscribe(data => { 
        this.formAutor.patchValue(data);
      }, err => {
        this.userService.getOne(this.tokenStorageService.getUser().username).subscribe(data => { this.formAutor.get('user').setValue(data); })
      });
    }
    this.podforumService.getOne(this.activatedRoute.snapshot.queryParams.podforum_id).subscribe(podforum => { this.podforum = podforum; this.formCreateEditTema.get('podforum').setValue(podforum); });
    
    this.addField();
  }

  get prilozi() {
    return this.formCreateEditObjava.controls["prilozi"] as FormArray;
  }

  addField() {
    const prilog = this.fb.group({
      opis: ['', Validators.required],
      url: ['', Validators.required]
    });
    this.prilozi.push(prilog);
  }

  deleteField(index: number) {
    if (this.prilozi.length !== 1) {
      this.prilozi.removeAt(index);
    }
  }

  save(){
    var trenutno_vreme = new Date();
    trenutno_vreme.setHours(trenutno_vreme.getHours() + 2);
    this.formCreateEditObjava.get('vremePostavljanja').setValue(trenutno_vreme);
    if(this.formAutor.value.vremePrijavljivanja == null) { this.formAutor.get('vremePrijavljivanja').setValue(trenutno_vreme); }

    if(this.formCreateEditTema.value.naziv != null && this.formCreateEditObjava.value.sadrzaj != null) {
      this.formAutor.value.objave += 1; // Svaki put kada postavi objavu povecamo za jedan
      if(this.formAutor.value.id == null) {
        this.korisnikNaForumuService.create(this.formAutor.value).subscribe(korisnikNaForumu => { 
          this.formCreateEditTema.get('autor').setValue(korisnikNaForumu); 
          this.formCreateEditObjava.get('autor').setValue(korisnikNaForumu);
          //Kreira se nova tema i objava u njoj
          this.temaService.create(this.formCreateEditTema.value).subscribe(tema => {
            this.formCreateEditObjava.get('tema').setValue(tema);
            this.objavaService.create(this.formCreateEditObjava.value).subscribe(objava => {      
              for(let file of this.formCreateEditObjava.value.prilozi) { // Kreiramo file sa referencom na objavu
                file.objava = objava;
                if(file.opis != '' && file.url != '' && file.objava != null) {
                  this.fileService.create(file).subscribe();
                }
              };
              this.cancel(); 
            });
          });
        });
      } else {
        this.korisnikNaForumuService.update(this.formAutor.value.id, this.formAutor.value).subscribe(korisnikNaForumu => { 
          this.formCreateEditTema.get('autor').setValue(korisnikNaForumu); 
          this.formCreateEditObjava.get('autor').setValue(korisnikNaForumu);
          //Kreira se nova tema i objava u njoj
          this.temaService.create(this.formCreateEditTema.value).subscribe(tema => {
            this.formCreateEditObjava.get('tema').setValue(tema);
            this.objavaService.create(this.formCreateEditObjava.value).subscribe(objava => {
              for(let file of this.formCreateEditObjava.value.prilozi) { // Kreiramo file sa referencom na objavu
                file.objava = objava;
                if(file.opis != '' && file.url != '' && file.objava != null) {
                  this.fileService.create(file).subscribe();
                }
              };
              this.cancel();
            });
          });
        });
      }
    }
    
    //Saljemo mail svim korisnicima koji su pretplaceni na podforum
    this.korisnikNaForumuService.getAllFollowPodforum(this.podforum.id).subscribe(korisniciNaForumu => { 
      for(let korisnik of korisniciNaForumu) {
        this.mailService.sendEmail(korisnik.user.email, "Upravo je postavljena nova tema u podforumu koji pratite (" + this.podforum.naziv + ")", 
          this.formAutor.value.user.name + " " + this.formAutor.value.user.surname + " je upravo postavio novu temu (" + this.formCreateEditTema.value.naziv + ") u podforumu koji pratite.").subscribe();
      }
    });

  }

  cancel(){
    this.formCreateEditTema.get("id")?.setValue(null);
    this.formCreateEditObjava.get("id")?.setValue(null);
    this.formCreateEditTema.reset();
    this.formCreateEditObjava.reset();
    this.location.back();
  }
}
