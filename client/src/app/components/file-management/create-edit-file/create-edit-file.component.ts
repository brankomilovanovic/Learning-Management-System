import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FileService } from 'src/app/service/file/file.service';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-edit-file',
  templateUrl: './create-edit-file.component.html',
  styleUrls: ['./create-edit-file.component.css']
})
export class CreateEditFileComponent implements OnInit {

  isFaild = false;
  errorMessage = '';

  fileToChangeID: number = -1;
  
  formCreateEditFile : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "opis" : new FormControl(null, [Validators.required]),
    "url" : new FormControl(null, [Validators.required])
  });
    
  constructor(private fileService : FileService, private location: Location, private route: ActivatedRoute) {}

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.fileToChangeID = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["id"];
      this.formCreateEditFile.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
    }
  }

  cancel(){
    this.formCreateEditFile.get("id")?.setValue(null);
    this.formCreateEditFile.reset();
    this.location.back();
  }

  save(){
    if(this.formCreateEditFile.valid){
      if(this.formCreateEditFile.value.id == null){
        this.fileService.create(this.formCreateEditFile.value).subscribe(x => { this.cancel(); });
      } else {
        this.fileService.update(this.formCreateEditFile.value.id, this.formCreateEditFile.value).subscribe(x => { this.cancel(); });
      }
    }
  }

  existFileByURL(){
    if(this.formCreateEditFile.get('url').valid){
      this.fileService.existFileByURL(this.fileToChangeID, this.formCreateEditFile.value.url).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditFile.controls.url.setErrors(null);
      }, err => {
        this.formCreateEditFile.controls.url.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }

}
