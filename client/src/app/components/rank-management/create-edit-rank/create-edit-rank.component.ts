import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { RankService } from 'src/app/service/rank/rank.service';
import { ScientificAreaService } from 'src/app/service/scientific-area/scientific-area.service';
import { StudentService } from 'src/app/service/student/student.service';
import { TypeRanksService } from 'src/app/service/type-ranks/type-ranks.service';
import { Location } from '@angular/common';
import { TypeRanks } from 'src/app/model/type-ranks';
import { ScientificArea } from 'src/app/model/scientific-area';
import { Professor } from 'src/app/model/professor';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/service/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { Rank } from 'src/app/model/rank';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-create-edit-rank',
  templateUrl: './create-edit-rank.component.html',
  styleUrls: ['./create-edit-rank.component.css']
})
export class CreateEditRankComponent implements OnInit {

  isCreateRankFailed = false;
  errorMessage = '';

  professors: Professor[] = [];
  typeRanksAll: TypeRanks[] = [];
  scientificAreas: ScientificArea[] = [];

  //for check rank for scientific area exist
  professorRanksList : Rank[] = [];
  editingRank : Rank = null; // we remember which area is already there, so that it can be saved because it already exists in the list

  role : String = "ROLE_ADMINISTRATOR";
  
  formCreateEditRanks : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "electionDate" : new FormControl(null, [Validators.required]),
    "terminationDate" : new FormControl(null),
    "typeRanks" : new FormControl(null, [Validators.required]),
    "scientificArea" : new FormControl(null, [Validators.required]),
    "professor" : new FormControl(null, [Validators.required])
  });
    
  constructor(private professorService : ProfessorService, private location: Location, private studentService: StudentService, private typeRanksService: TypeRanksService,
    private route: ActivatedRoute, private scientificAreaService: ScientificAreaService, private rankService: RankService, private userService : UserService,
    private tokenStorageService: TokenStorageService) {}

  ngOnInit(): void {

    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_PROFESSOR')){
      this.role = "ROLE_PROFESSOR"
    }

    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditRanks.patchValue(JSON.parse(this.route.snapshot.paramMap.get('rankForChange')));
      this.editingRank = JSON.parse(this.route.snapshot.paramMap.get('rankForChange'));
      this.rankService.getByUsername(JSON.parse(this.route.snapshot.paramMap.get('rankForChange')).professor.user.username).subscribe((rank: Rank[]) => { this.professorRanksList = rank; });
    }

    this.professorService.getAll().subscribe((professor: Professor[]) => { this.professors = professor; })
    this.typeRanksService.getAll().subscribe((typeRanks: TypeRanks[]) => { 
      for(let typeRank of typeRanks){
        if(typeRank.active == true){
          this.typeRanksAll.push(typeRank); 
        }
      }
    });
    this.scientificAreaService.getAll().subscribe((scientificAreas: ScientificArea[]) => { 
      for(let scientificArea of scientificAreas) {  
        if(scientificArea.active == true){
          this.scientificAreas.push(scientificArea);
        }
      }
    });
  }

  cancelCreateEditRanks(){
    this.formCreateEditRanks.get("id")?.setValue(null);
    this.formCreateEditRanks.reset();
    this.location.back();
  }

  saveRank(){
    if(this.formCreateEditRanks.valid){
      try{
        if (!this.professorRanksList.some((rank: Rank) => (rank.scientificArea.name == this.formCreateEditRanks.value.scientificArea.name) && 
          (this.editingRank.scientificArea.name != rank.scientificArea.name))) { //IF EXIST RANK FOR THAT SCIENTIFIC AREA
          if(this.formCreateEditRanks.value.id == null){
            this.rankService.create(this.formCreateEditRanks.value).subscribe(x => { this.cancelCreateEditRanks(); });
          } else {
            this.rankService.update(this.formCreateEditRanks.value.id, this.formCreateEditRanks.value).subscribe(x => { this.cancelCreateEditRanks(); });
          }
          this.editingRank = null;
        } else {
          this.errorMessage = "Ranks have already been awarded for this scientific area!"
          this.isCreateRankFailed = true;
        }
      } catch (err){
        this.errorMessage = "Ranks have already been awarded for this scientific area!"
        this.isCreateRankFailed = true;
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

  setProfessorForAddRank(profesor: Professor){
    this.rankService.getByUsername(profesor.user.username).subscribe((rank: Rank[]) => { this.professorRanksList = rank; });
  }
}
