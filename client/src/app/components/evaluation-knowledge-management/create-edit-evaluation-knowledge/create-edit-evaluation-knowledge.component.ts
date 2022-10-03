import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Topic } from 'src/app/model/topic';
import { Location } from '@angular/common';
import { TypeEvaluation } from 'src/app/model/type-evaluation';
import { TopicService } from 'src/app/service/topic/topic.service';
import { TypeEvaluationService } from 'src/app/service/type-evaluation/type-evaluation.service';
import { EvaluationKnowledgeService } from 'src/app/service/evaluation-knowledge/evaluation-knowledge.service';
import { EvaluationInstrument } from 'src/app/model/evaluation-instrument';
import { EvaluationInstrumentService } from 'src/app/service/evaluation-instrument/evaluation-instrument.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { SubjectService } from 'src/app/service/subject/subject.service';
import { Subject } from 'src/app/model/subject';
import { UserService } from 'src/app/service/user/user.service';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { User } from 'src/app/model/user';
import { Professor } from 'src/app/model/professor';
import { TeacherOnRealizationService } from 'src/app/service/teacher-on-realization/teacher-on-realization.service';
import { TeacherOnRealization } from 'src/app/model/teacher-on-realization';
import { SubjectRealizationService } from 'src/app/service/subject-realization/subject-realization.service';
import { SubjectRealization } from 'src/app/model/subject-realization';
import { EvaluationKnowledge } from 'src/app/model/evaluation-knowledge';


@Component({
  selector: 'app-create-edit-evaluation-knowledge',
  templateUrl: './create-edit-evaluation-knowledge.component.html',
  styleUrls: ['./create-edit-evaluation-knowledge.component.css']
})
export class CreateEditEvaluationKnowledgeComponent implements OnInit {

  isFaild = false;
  errorMessage = '';

  topics: Topic[] = [];
  typeEvaluations: TypeEvaluation[] = [];
  evaluationInstruments: EvaluationInstrument[] = [];
  subjectRealizations: SubjectRealization[] = [];

  classTimeToChangeID: number = -1;
  
  formCreateEditEvaluationKnowledge : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "vremePocetka" : new FormControl(null, [Validators.required]),
    "vremeKraja" : new FormControl(null, [Validators.required]),
    "bodovi" : new FormControl(null, [Validators.required]),
    "topic" : new FormControl(null, [Validators.required]),
    "typeEvaluation" : new FormControl(null, [Validators.required]),
    "evaluationInstrument" : new FormControl(null, [Validators.required]),
    "subjectRealization" : new FormControl(null, [Validators.required])
  });

  constructor(private topicService : TopicService, private location: Location, private typeEvaluationService: TypeEvaluationService, private route: ActivatedRoute,
    private evaluationKnowledgeService: EvaluationKnowledgeService, private evaluationInstrumentService: EvaluationInstrumentService, private tokenStorageService: TokenStorageService,
    private subjectRealizationService: SubjectRealizationService, private userService: UserService, private professorService: ProfessorService, private teacherOnRealizationService: TeacherOnRealizationService) {}

  ngOnInit(): void {
    
    if(window.location.href.indexOf("id") > -1) {
      this.formCreateEditEvaluationKnowledge.patchValue(JSON.parse(this.route.snapshot.paramMap.get('objectForChange')));
      this.classTimeToChangeID = JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))["id"];
      console.log(this.formCreateEditEvaluationKnowledge.value)
    }

    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_PROFESSOR')){
      this.subjectRealizationService.getByUsername(user.username).subscribe((objects: SubjectRealization[]) => { this.subjectRealizations = objects; });
    } else {
      this.subjectRealizationService.getAll().subscribe((objects: SubjectRealization[]) => { this.subjectRealizations = objects; });
    }

    this.topicService.getAll().subscribe((objects: Topic[]) => { 
      for(let topic of objects){
        if(topic.topicType.naziv.toString() == "EVALUACIJA_ZNANJA"){
          this.topics.push(topic);
        }
      }
    });
    this.typeEvaluationService.getAll().subscribe((objects: TypeEvaluation[]) => { this.typeEvaluations = objects; });
    this.evaluationInstrumentService.getAll().subscribe((objects: EvaluationInstrument[]) => { this.evaluationInstruments = objects; });
  }

  cancel(){
    this.formCreateEditEvaluationKnowledge.get("id")?.setValue(null);
    this.formCreateEditEvaluationKnowledge.reset();
    this.location.back();
  }

  save(){
    if(this.formCreateEditEvaluationKnowledge.valid){
      if(this.formCreateEditEvaluationKnowledge.value.id == null){
        this.evaluationKnowledgeService.create(this.formCreateEditEvaluationKnowledge.value).subscribe(x => { this.cancel(); });
      } else {
        this.evaluationKnowledgeService.update(this.formCreateEditEvaluationKnowledge.value.id, this.formCreateEditEvaluationKnowledge.value).subscribe(x => { this.cancel(); });
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
}
