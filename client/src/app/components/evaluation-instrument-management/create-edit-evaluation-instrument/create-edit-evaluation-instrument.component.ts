import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { File } from 'src/app/model/file';
import { EvaluationInstrumentService } from 'src/app/service/evaluation-instrument/evaluation-instrument.service';
import { FileService } from 'src/app/service/file/file.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-create-edit-evaluation-instrument',
  templateUrl: './create-edit-evaluation-instrument.component.html',
  styleUrls: ['./create-edit-evaluation-instrument.component.css']
})
export class CreateEditEvaluationInstrumentComponent implements OnInit {

  isFaild = false;
  errorMessage = '';

  files: File[] = [];
  evaluationInstrumentChangeID: number = -1;

  formCreateEditEvaluationInstrument : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "tipTestiranja" : new FormControl(null, [Validators.required]),
    "file" : new FormControl(null, [Validators.required])
  });

  constructor(private location: Location, private route: ActivatedRoute, private fileService: FileService, private evaluationInstrumentService: EvaluationInstrumentService) {}

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditEvaluationInstrument.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
      this.evaluationInstrumentChangeID = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["id"];
    }

    this.fileService.getAll().subscribe((objects: File[]) => { this.files = objects; })
  }

  cancel(){
    this.formCreateEditEvaluationInstrument.get("id")?.setValue(null);
    this.formCreateEditEvaluationInstrument.reset();
    this.location.back();
  }

  save(){
    if(this.formCreateEditEvaluationInstrument.valid){
      if(this.formCreateEditEvaluationInstrument.value.id == null){
        this.evaluationInstrumentService.create(this.formCreateEditEvaluationInstrument.value).subscribe(x => { this.cancel(); });
      } else {
        this.evaluationInstrumentService.update(this.formCreateEditEvaluationInstrument.value.id, this.formCreateEditEvaluationInstrument.value).subscribe(x => { this.cancel(); });
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

  checkEvaluationInstrumentExistWithFile(file : File){
    if(this.formCreateEditEvaluationInstrument.get("file").valid == true){
      this.evaluationInstrumentService.existsByFileID(this.evaluationInstrumentChangeID, file.id).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditEvaluationInstrument.controls.file.setErrors(null);
      }, err => {
        this.formCreateEditEvaluationInstrument.controls.file.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }

  checkEvaluationInstrumentOpisExist(tipTestiranja : String){
    if(this.formCreateEditEvaluationInstrument.get("tipTestiranja").valid == true){
      this.evaluationInstrumentService.existsByTipTestiranja(this.evaluationInstrumentChangeID, tipTestiranja).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditEvaluationInstrument.controls.tipTestiranja.setErrors(null);
      }, err => {
        this.formCreateEditEvaluationInstrument.controls.tipTestiranja.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }

}
