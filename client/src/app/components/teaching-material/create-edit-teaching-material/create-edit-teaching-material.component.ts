import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { File } from 'src/app/model/file';
import { FileService } from 'src/app/service/file/file.service';
import { TeachingMaterialService } from 'src/app/service/teaching-material/teaching-material.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-create-edit-teaching-material',
  templateUrl: './create-edit-teaching-material.component.html',
  styleUrls: ['./create-edit-teaching-material.component.css']
})
export class CreateEditTeachingMaterialComponent implements OnInit {

  isFailed = false;
  errorMessage = '';

  files: File[] = [];

  teachingMaterialToChangeID: number = -1;
  uniqueAuthors = new Map();

  formCreateEdit : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "naziv" : new FormControl(null, [Validators.required]),
    "godinaIzdavanja" : new FormControl(null),
    "autori" : new FormArray([new FormControl(null, Validators.required)]),
    "file" : new FormControl(null, [Validators.required])
  });
    
  constructor(private location: Location, private fileService: FileService, private teachingMaterialService: TeachingMaterialService,
    private route: ActivatedRoute) {}

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEdit.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
      this.teachingMaterialToChangeID = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["id"];
      
      let index : number = 0;
      for(let i of JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["autori"]){
        if(index > 0){ this.addAuthorField(); } //Pravi novo polje za setovanje autora
        this.autori().at(index).patchValue(i) //Setuje autora u novo polje koje je napravljeno
        index ++;      
      }
    }
      
    this.fileService.getAll().subscribe((objects: File[]) => { this.files = objects; })
  }

  cancel(){
    this.formCreateEdit.get("id")?.setValue(null);
    this.formCreateEdit.reset();
    this.location.back();
  }

  autori() : FormArray {
    return this.formCreateEdit.get("autori") as FormArray
  }

  addAuthorField() {
    this.autori().push(new FormControl('', Validators.required));
  }

  deleteAuthorField(index: number) {
    if (this.autori().length !== 1) {
      this.autori().removeAt(index);
    }
  }

  save(){
    if(this.formCreateEdit.valid){
      if(this.formCreateEdit.value.id == null){
        this.teachingMaterialService.create(this.formCreateEdit.value).subscribe(x => { this.cancel(); });
      } else {
        this.teachingMaterialService.update(this.formCreateEdit.value.id, this.formCreateEdit.value).subscribe(x => { this.cancel(); });
      }
    }
  }

  comparator(v1: any, v2: any) {
    if(v1 && v2)
    {
      return v1["id"] == v2["id"]
    } else {
      return  v1 == v2
    }
  }

  checkExistsNaziv(naziv : String){
    if(this.formCreateEdit.get("naziv").valid == true){
      this.teachingMaterialService.existsByNaziv(this.teachingMaterialToChangeID, naziv).subscribe(data => {
        this.isFailed = false;
        this.formCreateEdit.controls.naziv.setErrors(null);
      }, err => {
        this.formCreateEdit.controls.naziv.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

  findDuplicateAuthor(author: String): boolean {
    let existsAuthor = this.autori().controls.filter(data => data.value == author && author != null)
    if (existsAuthor.length > 1) {
      this.formCreateEdit.controls.autori.setErrors({'incorrect': true});
      return true;
    } else {
      this.formCreateEdit.controls.autori.setErrors(null);
      return false
    }
  }
}
