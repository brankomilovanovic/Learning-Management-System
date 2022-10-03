import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ForumService } from 'src/app/service/forum/forum/forum.service';
import { Location } from '@angular/common';
import { PodforumService } from 'src/app/service/forum/podforum/podforum.service';
import { KorisnikNaForumuService } from 'src/app/service/forum/korisnik-na-forumu/korisnik-na-forumu.service';

@Component({
  selector: 'app-create-edit-forum',
  templateUrl: './create-edit-forum.component.html',
  styleUrls: ['./create-edit-forum.component.css']
})
export class CreateEditForumComponent implements OnInit {

  isFailed = false;
  errorMessage = '';

  podforumToChangeID: number = -1;

  formCreatePodforum : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "naziv" : new FormControl(null, [Validators.required]),
    "opis" : new FormControl(null, [Validators.required]),
    "forum" : new FormControl(null, [Validators.required])
  });
    
  constructor(private location: Location, private route: ActivatedRoute, private forumService: ForumService, private podforumService: PodforumService,
    private korisnikNaForumu: KorisnikNaForumuService) {}

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreatePodforum.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
      this.podforumToChangeID = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["id"];
    }
    this.forumService.getForum().subscribe(forum => { this.formCreatePodforum.get('forum').setValue(forum); });
  }

  cancel(){
    this.formCreatePodforum.get("id")?.setValue(null);
    this.formCreatePodforum.reset();
    this.location.back();
  }

  save(){
    if(this.formCreatePodforum.valid){
      if(this.formCreatePodforum.value.id == null){
        this.podforumService.create(this.formCreatePodforum.value).subscribe(x => { this.cancel(); });
      } else {
        this.podforumService.update(this.formCreatePodforum.value.id, this.formCreatePodforum.value).subscribe(x => { this.cancel(); });
      }
    }
  }

  checkName(){
    if(this.formCreatePodforum.get("naziv").valid == true){
      this.podforumService.existByName(this.podforumToChangeID, this.formCreatePodforum.value.naziv).subscribe(data => {
        this.isFailed = false;
        this.formCreatePodforum.controls.naziv.setErrors(null);
      }, err => {
        this.formCreatePodforum.controls.naziv.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }



}
