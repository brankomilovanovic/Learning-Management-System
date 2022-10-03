import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ScientificAreaService } from 'src/app/service/scientific-area/scientific-area.service';

@Component({
  selector: 'app-create-edit-scientific-area',
  templateUrl: './create-edit-scientific-area.component.html',
  styleUrls: ['./create-edit-scientific-area.component.css']
})
export class CreateEditScientificAreaComponent implements OnInit {

  isFaild = false;
  errorMessage = '';

  facultyToChangeID: number = -1;

  formCreateEditScientificArea : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "name" : new FormControl(null, [Validators.required]),
    "active" : new FormControl(true)
  });

  constructor(private route: ActivatedRoute, private location: Location, private scientificAreaService: ScientificAreaService) { }

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditScientificArea.patchValue(JSON.parse(this.route.snapshot.paramMap.get('scientificAreaForChange')));
      this.facultyToChangeID = JSON.parse(this.route.snapshot.paramMap.get('scientificAreaForChange'))["id"];
    }
  }

  save(){
    if(this.formCreateEditScientificArea.valid){
      if(this.formCreateEditScientificArea.value["id"] == null){
        this.scientificAreaService.create(this.formCreateEditScientificArea.value).subscribe(rank => { this.cancel(); });
      } else {
        this.scientificAreaService.update(this.formCreateEditScientificArea.value.id, this.formCreateEditScientificArea.value).subscribe(rank => { this.cancel(); });
      }
    }
  };

  checkName(){
    if(this.formCreateEditScientificArea.get("name").valid == true){
      this.scientificAreaService.existByName(this.facultyToChangeID, this.formCreateEditScientificArea.value.name).subscribe(data => {
        this.isFaild = false;
        this.formCreateEditScientificArea.controls.name.setErrors(null);
      }, err => {
        this.formCreateEditScientificArea.controls.name.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFaild = true;
      });
    }
  }

  cancel(){
    this.formCreateEditScientificArea.get("id")?.setValue(null);
    this.formCreateEditScientificArea.reset();
    this.location.back();
  }
}
