import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { TypeRanksService } from 'src/app/service/type-ranks/type-ranks.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-create-edit-type-ranks',
  templateUrl: './create-edit-type-ranks.component.html',
  styleUrls: ['./create-edit-type-ranks.component.css']
})
export class CreateEditTypeRanksComponent implements OnInit {

  isFailed = false;
  errorMessage = '';

  typeRankToChangeID: number = -1;

  formCreateEditTypeRank : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "name" : new FormControl(null, [Validators.required]),
    "active" : new FormControl(true)
  });

  constructor(private route: ActivatedRoute, private location: Location, private typeRanksService: TypeRanksService) { }

  ngOnInit(): void {
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditTypeRank.patchValue(JSON.parse(this.route.snapshot.paramMap.get('typeRankForChange')));
      this.typeRankToChangeID = JSON.parse(this.route.snapshot.paramMap.get('typeRankForChange'))["id"];
    }
  }

  saveRank(){
    if(this.formCreateEditTypeRank.valid){
      if(this.formCreateEditTypeRank.value["id"] == null){
        this.typeRanksService.create(this.formCreateEditTypeRank.value).subscribe(rank => { this.cancel(); });
      } else {
        this.typeRanksService.update(this.formCreateEditTypeRank.value.id, this.formCreateEditTypeRank.value).subscribe(rank => { this.cancel(); });
      }
    }
  };

  checkName(){
    if(this.formCreateEditTypeRank.value.name != null){
      this.typeRanksService.existByName(this.typeRankToChangeID, this.formCreateEditTypeRank.value.name).subscribe(data => {
        this.isFailed = false;
        this.formCreateEditTypeRank.controls.name.setErrors(null);
      }, err => {
        this.formCreateEditTypeRank.controls.name.setErrors({'incorrect': true});
        this.errorMessage = err.error.message;
        this.isFailed = true;
      });
    }
  }

  cancel(){
    this.formCreateEditTypeRank.get("id")?.setValue(null);
    this.formCreateEditTypeRank.reset();
    this.location.back();
  }

}
