import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { NgxNotificationMsgService } from 'ngx-notification-msg';
import { Subject } from 'src/app/model/subject';
import { TakingExam } from 'src/app/model/taking-exam';
import { SubjectNotifications } from 'src/app/model/subject-notifications';
import { FollowService } from 'src/app/service/follow-sub/follow.service';
import { StudentServiceService } from 'src/app/service/student-service/student-service.service';
import { SubjectNotificationsService } from 'src/app/service/subject-notifications/subject-notifications.service';
import { SubjectService } from 'src/app/service/subject/subject.service';
import { SubjectNotificationsDialog } from '../../board-moderator/about-subject/about-subject.component';
import { Location } from '@angular/common';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { SubjectRealizationService } from 'src/app/service/subject-realization/subject-realization.service';
import { StudentOnTheYearService } from 'src/app/service/student-on-the-year/student-on-the-year.service';
import { EvaluationKnowledgeService } from 'src/app/service/evaluation-knowledge/evaluation-knowledge.service';
import { EvaluationKnowledge } from 'src/app/model/evaluation-knowledge';
import { TakingExamService } from 'src/app/service/taking-exam/taking-exam.service';
import { StudentOnTheYear } from 'src/app/model/student-on-the-year';
import { StudentTests } from 'src/app/model/student-tests';
import { StudentService } from 'src/app/model/student-service';

@Component({
  selector: 'app-subject-details',
  templateUrl: './subject-details.component.html',
  styleUrls: ['./subject-details.component.css']
})
export class SubjectDetailsComponent implements OnInit {
  
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatAccordion) accordion: MatAccordion;
  
  displayedColumnsNotifications = ['id', 'naziv', 'opis'];
  dataSourceNotifications: MatTableDataSource<SubjectNotifications>;

  displayedColumns = ['naziv', 'godinaIzdavanja', 'autori'];
  displayedColumnsEvaluationKnowledges = ['naziv', 'tipEvaluacije', 'vremePocetka', 'bodovi'];

  subject: string;
  studentOnTheYear: StudentOnTheYear = null;

  takingExam: TakingExam = Object.create({ id: null, bodovi: null, napomena: null, studentOnTheYear: null, evaluationKnowledge: null });
  
  predmet: Subject;
  kliknut = false;

  klik: boolean = false;

  test: StudentTests[]=[];
  upisi: StudentService[]=[];
  student: StudentOnTheYear;

  evaluationKnowledges : EvaluationKnowledge[] = [];

  formCreateSilabus : FormGroup = new FormGroup({
    "brojPredavanja" : new FormControl(null),
    "brojVezbi" : new FormControl(null),
    "drugiObliciNastave" : new FormControl(null),
    "espb" : new FormControl(null),
    "id" : new FormControl(null),
    "istrazivackiRad" : new FormControl(null),
    "naziv" : new FormControl(null),
    "obavezan" : new FormControl(null),
    "ostaliCasovi" : new FormControl(null),
    "semester" : new FormControl(null),
    "silabus" : new FormControl(null),
    "topic" : new FormControl(null)
  });

  constructor(private router: Router, private route: ActivatedRoute, private subservice: SubjectService, private readonly ngxNotificationMsgService: NgxNotificationMsgService,
    private evaluationKnowledgeService: EvaluationKnowledgeService, public dialog: MatDialog, private location: Location, private subjectServiceNotifications: SubjectNotificationsService,
    private tokenStorageService: TokenStorageService, private studentOnTheYearService: StudentOnTheYearService, private takingExamService: TakingExamService,
    private sy: StudentOnTheYearService) { }
    
    ngOnInit(): void {

      const user = this.tokenStorageService.getUser();
      if(window.location.href.indexOf("id") > -1) {
        this.formCreateSilabus.patchValue(JSON.parse(this.route.snapshot.paramMap.get('subforchange')));
        this.subject = this.formCreateSilabus.value.naziv
        this.predmet = (JSON.parse(this.route.snapshot.paramMap.get('subforchange')));
        console.log(this.predmet)

        this.subjectServiceNotifications.getBySubjectId(this.predmet.id).subscribe(x => { 
          this.dataSourceNotifications = new MatTableDataSource(x.slice());
          this.dataSourceNotifications.sort = this.sort;
          });

        this.studentOnTheYearService.getOne(user.id).subscribe(student => {
          this.studentOnTheYear = student;
          this.refreshTestList(student.id, this.predmet.id);
        });

      }
    }

    refreshTestList(studentId: number, predmetId: number){
      this.evaluationKnowledgeService.getAllUndoneTests(studentId, predmetId).subscribe(x => { this.evaluationKnowledges = x; });
    }          

    editNotification(subjectNotification : SubjectNotifications) {
      this.dialog.open(SubjectNotificationsDialog, { 
        width: '650px', 
        data: subjectNotification ,
      });
    }

    about(){
      this.formCreateSilabus.get("id")?.setValue(null);
      this.formCreateSilabus.reset();
      this.router.navigate(['user']);
    }

    goFile(url: string) {
      window.open(url, "_blank");
    }

    doTest(evaluation){
      this.takingExam.bodovi = Math.floor(Math.random() * (evaluation.bodovi - 1 + 1) + 1);
      this.takingExam.evaluationKnowledge = evaluation;
      this.takingExam.studentOnTheYear = this.studentOnTheYear;
      if(this.takingExam.bodovi >= evaluation.bodovi / 2){
        this.takingExam.napomena = "Polozio";
        alert("Uspesno ste polozili ovaj test!\nBodovi: " + this.takingExam.bodovi + "/" + evaluation.bodovi)
      } else {
        this.takingExam.napomena = "Nije polozio";
        alert("Nazalost niste polozili ovaj ispit!\nBodovi: " + this.takingExam.bodovi + "/" + evaluation.bodovi)
      }

      this.takingExamService.create(this.takingExam).subscribe(x => { 
        this.refreshTestList(this.studentOnTheYear.id, this.predmet.id); 
        this.takingExam = Object.create({ id: null, bodovi: null, napomena: null, studentOnTheYear: null, evaluationKnowledge: null }); 
      });
    }

    history(){
      const user = this.tokenStorageService.getUser();
      this.klik = true;
      //console.log(this.studentFromService.student.student.user.id)
       //ZA ISTORIJU STUDIRANJA
        //this.kliknut = true;
        this.test = [];
        // if(this.bodovi > 0){
        //   this.bodovi=0;
        // }
        this.sy.getSubjectsByID(user.id).subscribe(x=>{
          this.upisi = x;
          console.log("UPISI",x);
        })
    
        this.sy.getOne(user.id).subscribe(x=>{
          this.student = x;
          let u = x;
          for(let r of u.studentTests){
            this.test.push(r);
            // this.city = String(x.student.address.city);
            // this.country = String(x.student.address.country);
            // this.steet = String(x.student.address.streetAndNumber);
    
            //console.log("ADRESA", this.adresa)
            
        }for(let b of u.studentTests){
          let testovi = [];
          testovi.push(b);
          
          //this.bodovi += b.ocena / this.test.length;
        }
        });
      
    }

  }

