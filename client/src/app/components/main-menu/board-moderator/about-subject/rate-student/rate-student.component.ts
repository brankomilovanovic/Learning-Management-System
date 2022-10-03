import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentService } from 'src/app/model/student-service';
import { Location } from '@angular/common';
import { TakingExam } from 'src/app/model/taking-exam';
import { StudentServiceService } from 'src/app/service/student-service/student-service.service';
import { TakingExamService } from 'src/app/service/taking-exam/taking-exam.service';
import { StudentTestService } from 'src/app/service/student-test/student-test.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgxNotificationMsgService, NgxNotificationStatusMsg } from 'ngx-notification-msg';
import { SubjectService } from 'src/app/service/subject/subject.service';
import { Subject } from 'src/app/model/subject';
import { StudentTests } from 'src/app/model/student-tests';
import { StudentOnTheYearService } from 'src/app/service/student-on-the-year/student-on-the-year.service';
import { StudentOnTheYear } from 'src/app/model/student-on-the-year';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import * as FileSaver from 'file-saver';
import beautify from "xml-beautifier";
import * as converter from 'xml-js';

@Component({
  selector: 'app-rate-student',
  templateUrl: './rate-student.component.html',
  styleUrls: ['./rate-student.component.css']
})
export class RateStudentComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['id', 'bodovi', 'napomena', 'radjen', 'tip', 'student'];
  dataSource: MatTableDataSource<TakingExam>;

  studentFromService : StudentService = null;
  subject : Subject = null;
  takingExams: TakingExam[] = [];

  dozvoljenUnosOcene : boolean = false;

  kliknut: boolean = false;

  test: StudentTests[]=[];
  upisi: StudentService[]=[];
  student: StudentOnTheYear;

  form : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "kolokvijum1" : new FormControl(0),
    "kolokvijum2" : new FormControl(0),
    "ispit" : new FormControl(0),
    "aktivnost" : new FormControl(0),
    "ocena" : new FormControl(5),
    "studentOnTheYear" : new FormControl(null, [Validators.required]),
    "subject" : new FormControl(null, [Validators.required]),
  });

  constructor(private route: ActivatedRoute, private studentServiceService: StudentServiceService, private takingExamService: TakingExamService,
    private studentTestsService: StudentTestService, private location: Location, private readonly ngxNotificationMsgService: NgxNotificationMsgService, private subjectService: SubjectService,
    private sy: StudentOnTheYearService) { }

  ngOnInit(): void {
    if(window.location.href.indexOf("studentServiceID") > -1) {
      this.studentServiceService.getSubjectsByID(JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))['studentServiceID']).subscribe(studentService => { 
        for(let student of studentService) { this.studentFromService = student; }
        this.subjectService.getOne(JSON.parse(this.route.snapshot.paramMap.get('objectForChange'))['subjectID']).subscribe(subject => {
          this.subject = subject;
          this.setObjectsInTable();
        });
      });
    }
  }

  history(){
    this.kliknut = true;
    console.log(this.studentFromService.student.student.user.id)
     //ZA ISTORIJU STUDIRANJA
      //this.kliknut = true;
      this.test = [];
      // if(this.bodovi > 0){
      //   this.bodovi=0;
      // }
      this.sy.getSubjectsByID(this.studentFromService.student.student.user.id).subscribe(x=>{
        this.upisi = x;
        console.log("UPISI",x);
      })
  
      this.sy.getOne(this.studentFromService.student.student.user.id).subscribe(x=>{
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

  setObjectsInTable(){
    this.form.patchValue({["studentOnTheYear"] : this.studentFromService.student});
    this.form.patchValue({["subject"] : this.subject});

    this.takingExamService.getByStudentOnTheYearAndSubject(this.studentFromService.student.id, this.subject.id).subscribe((takingExams : TakingExam[]) => {
      //console.log(this.studentFromService.student.student.user.id)
      //Provera da li je proslo vise od 15 dana od poslednjeg testa
      this.takingExams = takingExams
      // console.log(takingExams);
      let datumi_polaganja = [];
      for(let i of takingExams){ datumi_polaganja.push(i.evaluationKnowledge.vremePocetka) }; // Ubacujemo sva vremena polaganja testova
      if(datumi_polaganja.length != 0) { // Ako nema ni jedno polaganje automatski mu je zabranjen unos ocene
        let najvecidatum = this.parseDate(datumi_polaganja.sort((a,b) => new Date(b).getTime() - new Date(a).getTime())[0]);
        let trenutnidatum =  this.parseDate(new Date(Date.now()).toISOString().split('T')[0]);
        let dani = (trenutnidatum.getTime() - najvecidatum.getTime()) / (1000 * 3600 * 24);
        if(dani <= 15) { this.dozvoljenUnosOcene = true; }
      }
      this.dataSource = new MatTableDataSource(takingExams.slice());
      this.dataSource.sort = this.sort;
    });
    this.studentTestsService.getByStudentOnTheYearAndSubject(this.studentFromService.student.id, this.subject.id).subscribe(studentTest => { this.form.patchValue(studentTest); this.ocena(); }, err => { console.log("Korisnik nema ocene!") })
  }

  cancel(){
    this.form.reset();
    this.form.get("id")?.setValue(null);
    this.location.back();
  }

  save(){
    if(this.form.valid){
      if(this.dozvoljenUnosOcene == true) {
        if(this.form.value["id"] == null){
          this.studentTestsService.create(this.form.value).subscribe();
        } else {
          this.studentTestsService.update(this.form.value.id, this.form.value).subscribe();
        }
        this.ngxNotificationMsgService.open({
          status: NgxNotificationStatusMsg.SUCCESS,
          header: 'Rate student',
          messages: ['You have successfully rated student.']
        });
      } else {
        this.ngxNotificationMsgService.open({
          status: NgxNotificationStatusMsg.FAILURE,
          header: 'Rate student',
          messages: ['More than 15 days have passed since the last test, or the student has not taken a single test.']
        });
      }
    }
  };

  ocena(){
    let zbir: number = this.form.value.kolokvijum1 + this.form.value.kolokvijum2 + this.form.value.ispit + this.form.value.aktivnost
    if(zbir < 50){
      this.form.patchValue({["ocena"] : 5});
    }
    else if(zbir > 50 && zbir < 61){
      this.form.patchValue({["ocena"] : 6});
    }
    else if(zbir > 60 && zbir < 71){
      this.form.patchValue({["ocena"] : 7});
    }
    else if(zbir > 70 && zbir < 81){
      this.form.patchValue({["ocena"] : 8});
    }
    else if(zbir > 80 && zbir < 91){
      this.form.patchValue({["ocena"] : 9});
    }
    else if(zbir > 90){
      this.form.patchValue({["ocena"] : 10});
    }
  }

  parseDate(input) { // Pretravamo iz string date u date tip
    var parts = input.match(/(\d+)/g);
    return new Date(parts[0], parts[1]-1, parts[2]); // months are 0-based
  }

  exportPDF(){
    setTimeout(()=>{                           
      const doc = new jsPDF('p', 'px', 'a4');
     autoTable(doc, {html:'#content'});
     doc.save(this.takingExams[0].studentOnTheYear.indexNo +  ".pdf");
  }, 10);
  setTimeout(()=>{                           
    // this.show = false;
  }, 10);
  }

  exportXML() {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    window?.Buffer = window?.Buffer || require("buffer").Buffer
    var evaluation = this.takingExams
    // console.log(evaluation);
    // if (eva.length < 0) {  
      if(evaluation.length > 0){
        var evaluations = {evaluation};
      

       
        var str = JSON.stringify({evaluations});

        var xml = beautify(str);

        
        var outputXml = converter.json2xml(xml, {compact: true, spaces: 4});

            var fileToExport = new Blob([outputXml], {type: "text/xml"});
            window.open(URL.createObjectURL(fileToExport));
            FileSaver.saveAs(fileToExport, "evaluation-res.xml");
      }
  

}

  generate(max, min){
    let broj = Math. floor(Math. random() * (max - min + 1)) + min;
    return broj
  }

  selectFile(event){
    const reader = new FileReader();
    reader.onload = (e: any) => {
      let xml = e.target.result;
      let result1 = converter.xml2json(xml, {compact: true, spaces: 4});

      var JSONData = JSON.parse(result1);
    
      var arr = JSONData.evaluations.evaluation;
      // this.takingExams.patchValue(JSONData)
      var novi;
      var data;
      // console.log(this.takingExams);
      let broj;
      for(data of arr){
        let t = this.generate(10000, 1);
        console.log(t)
        if(parseInt(data.id._text) != t){
          broj = t;
        }
        // this.takingExams.push(data)
        // data.id
        novi = {
          "id" : broj
          ,
          "bodovi": parseInt(data.bodovi._text),
          "napomena": data.napomena._text,
          "studentOnTheYear" :{
          "dateOfEntry" : data.studentOnTheYear.dateOfEntry._text,
          "indexNo" : data.studentOnTheYear.indexNo._text,
          "student": {
            "id" : parseInt(data.studentOnTheYear.student.id._text)+ arr.length
          }
          },
          "evaluationKnowledge": {
            "bodovi" : parseInt(data.evaluationKnowledge.bodovi._text),
            "evaluationInstrument": null,
            "id": null,
            "subjectRealization": null,
            "takingExam": null,
            "topic": null,
            "typeEvaluation" : {
              "evaluationKnowledge" : [],
              "id": parseInt(data.evaluationKnowledge.typeEvaluation.id._text)+ arr.length,
              "tipEvaluacije": data.evaluationKnowledge.typeEvaluation.tipEvaluacije._text

            },
            "vremeKraja": null,
            "vremePocetka": data.evaluationKnowledge.vremePocetka._text
            
          }
        }

        this.takingExams.push(novi)
      }

      setTimeout(()=>{                           

      }, 10);
      this.dataSource = new MatTableDataSource(this.takingExams.slice());
      this.dataSource.sort = this.sort;

      
		}
    reader.readAsText(event.target.files[0])
  }
}
