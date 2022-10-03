import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Professor } from 'src/app/model/professor';
import { TypeOfTeaching } from 'src/app/model/type-of-teaching';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { TeacherOnRealizationService } from 'src/app/service/teacher-on-realization/teacher-on-realization.service';
import { TypeOfTeachingService } from 'src/app/service/type-of-teaching/type-of-teaching.service';
import { Location } from '@angular/common';
import { TeacherOnRealization } from 'src/app/model/teacher-on-realization';

@Component({
  selector: 'app-create-edit-teacher-on-realization',
  templateUrl: './create-edit-teacher-on-realization.component.html',
  styleUrls: ['./create-edit-teacher-on-realization.component.css']
})
export class CreateEditTeacherOnRealizationComponent implements OnInit {

  isCreateFailed = false;
  errorMessage = '';

  professors: Professor[] = [];
  typeOfTeachingAll: TypeOfTeaching[] = [];
  teacherOnRealization: any = [];
  teacherOnRealizationSelectedForEdit : any = [];

  formCreateEditTeacherOnRealization : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "brojCasova" : new FormControl(null, [Validators.required]),
    "professor" : new FormControl(null, [Validators.required]),
    "typeOfTeaching" : new FormControl(null, [Validators.required])
  });
    
  constructor(private professorService : ProfessorService, private location: Location, private typeOfTeachingService: TypeOfTeachingService, 
    private teacherOnRealizationService: TeacherOnRealizationService, private route: ActivatedRoute) {}

  ngOnInit(): void {

    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditTeacherOnRealization.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
      this.teacherOnRealizationSelectedForEdit = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'));
      this.refreshProfessorAndTypeOfTeaching(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')).professor.id);

    }
    this.professorService.getAll().subscribe((objects: Professor[]) => { this.professors = objects; });
    this.typeOfTeachingService.getAll().subscribe((objects: TypeOfTeaching[]) => { this.typeOfTeachingAll = objects; });
  }

  cancel(){
    this.location.back();
    this.formCreateEditTeacherOnRealization.get("id")?.setValue(null);
    this.formCreateEditTeacherOnRealization.reset();
  }

  save(){
    if(this.formCreateEditTeacherOnRealization.valid){
      //PROVER DA LI VEC POSTOJI PROVESOR SA OVIM TIPOM NASTAVE
      this.errorMessage = "";
      this.isCreateFailed = false;
      this.formCreateEditTeacherOnRealization.controls.typeOfTeaching.setErrors(null);
      for(let teacher of this.teacherOnRealization){
       
        teacher.typeOfTeaching.sort(function(a: { id: string; }, b: { id: string; }) { return parseFloat(a.id) - parseFloat(b.id); }); //sortiramo da bi stringify radio kako treba, uvek proveravao istim redosledom
        this.formCreateEditTeacherOnRealization.value.typeOfTeaching.sort(function(a: { id: string; }, b: { id: string; }) { return parseFloat(a.id) - parseFloat(b.id); }); //sortiramo da bi stringify radio kako treba, uvek proveravao istim redosledom
        
        if(this.teacherOnRealizationSelectedForEdit.typeOfTeaching != undefined){ // provera da li je edit ili dodavanje novog provesora na realizaicju
          this.teacherOnRealizationSelectedForEdit.typeOfTeaching.sort(function(a: { id: string; }, b: { id: string; }) { return parseFloat(a.id) - parseFloat(b.id); }); //sortiramo da bi stringify radio kako treba, uvek proveravao istim redosledom
          if(((JSON.stringify(teacher.typeOfTeaching) == JSON.stringify(this.formCreateEditTeacherOnRealization.value.typeOfTeaching)) &&
          JSON.stringify(teacher.typeOfTeaching) != JSON.stringify(this.teacherOnRealizationSelectedForEdit.typeOfTeaching))){
            this.errorMessage = "There is already a professor with that type of teaching!";
            this.isCreateFailed = true;
            this.formCreateEditTeacherOnRealization.controls.typeOfTeaching.setErrors({'incorrect': true});
          }
        } else {
          if((JSON.stringify(teacher.typeOfTeaching) == JSON.stringify(this.formCreateEditTeacherOnRealization.value.typeOfTeaching))){
            this.errorMessage = "There is already a professor with that type of teaching!";
            this.isCreateFailed = true;
            this.formCreateEditTeacherOnRealization.controls.typeOfTeaching.setErrors({'incorrect': true});
          }
        }
        
      }
      ///KRAJ PROVERE

      if(this.isCreateFailed == false){
        if(this.formCreateEditTeacherOnRealization.value.id == null){
          this.teacherOnRealizationService.create(this.formCreateEditTeacherOnRealization.value).subscribe(x => { this.cancel(); });
        } else {
            this.teacherOnRealizationService.update(this.formCreateEditTeacherOnRealization.value.id, this.formCreateEditTeacherOnRealization.value).subscribe(x => { this.cancel(); });
        }
      }
    }
  }

  refreshProfessorAndTypeOfTeaching(id: number){ // dobijamo sve profesore i nihove tipove nastava, da ne bi mogli imati jednog profesora koji ima isti tip nastava
    this.teacherOnRealizationService.getByProfessorId(id).subscribe((objects : TeacherOnRealization[]) => {
      this.teacherOnRealization = objects;
    })
  }

  comparator(v1: any, v2: any) {
    if(v1 && v2)
    {
      return v1["id"] == v2["id"]
    } else {
      return  v1 == v2
    }
  }

  compare(topic1: any, topic2: any) {
    return topic1 && topic2
      ? topic1.id === topic2.id
      : topic1 === topic2;
  }
}
