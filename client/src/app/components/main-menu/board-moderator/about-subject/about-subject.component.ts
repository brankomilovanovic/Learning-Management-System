import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectService } from 'src/app/service/subject/subject.service';
import { SubjectNotificationsService } from 'src/app/service/subject-notifications/subject-notifications.service';
import { NgxNotificationMsgService, NgxNotificationStatusMsg } from 'ngx-notification-msg';
import { FollowService } from 'src/app/service/follow-sub/follow.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { StudentServiceService } from 'src/app/service/student-service/student-service.service';
import { StudentService } from 'src/app/model/student-service';
import { SelectionModel } from '@angular/cdk/collections';
import { Subject } from 'src/app/model/subject';
import { SubjectNotifications } from 'src/app/model/subject-notifications';
import { MatAccordion } from '@angular/material/expansion';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Professor } from 'src/app/model/professor';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-about-subject',
  templateUrl: './about-subject.component.html',
  styleUrls: ['./about-subject.component.css']
})
export class AboutSubjectComponent implements OnInit {

  //table for students
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatAccordion) accordion: MatAccordion;

  displayedColumns = ['indexNo', 'name', 'surname', 'dateOfEntry'];
  dataSource: MatTableDataSource<StudentService>;
  TotalRow: number;  
  selection = new SelectionModel <StudentService> (true, []);  

  displayedColumnsNotifications = ['id', 'naziv', 'opis'];
  dataSourceNotifications: MatTableDataSource<SubjectNotifications>;
  
  //SEARCH
  searchForm : FormGroup = new FormGroup({
  "searchNameSurname" : new FormControl(null),
  "searchIndexNo" : new FormControl(null),
  "searchDate" : new FormControl(null)
  });
  searchNameSurname = '';
  searchIndexNo = '';
  searchDate = '';

  subject: string;
  
  predmet: Subject = null;
  kliknut = false;

  subjectID: number = -1; //ID subjekta preko kojeg dobijamo studente 
  students: StudentService[] = [];
  subjectNotifications: SubjectNotifications[] = [];

  professor: Professor = null;

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
    private followSubjectService: FollowService, private studentServiceService: StudentServiceService, private subjectServiceNotifications: SubjectNotificationsService,
    public dialog: MatDialog, private tokenStorageService: TokenStorageService, private professorService: ProfessorService,
    public us: UserService) { }
  
  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    this.professorService.getByUsername(user.username).subscribe(professor => { this.professor = professor; })

    if(window.location.href.indexOf("id") > -1) {
      this.formCreateSilabus.patchValue(JSON.parse(this.route.snapshot.paramMap.get('subforchange')));
      this.subject = this.formCreateSilabus.value.naziv
      this.subjectID = JSON.parse(this.route.snapshot.paramMap.get('subforchange'))["id"];
      this.loadStudents();
      this.subservice.getOne(JSON.parse(this.route.snapshot.paramMap.get('subforchange'))["id"]).subscribe((x: Subject) => { this.predmet = x; this.setNotificationsData(x.id); });
    }
  }

  addNotification() {
    this.dialog.open(SubjectNotificationsDialog, { 
      width: '650px', 
      data: { subject: this.predmet, professor: this.professor },
    });
  }

  editNotification(subjectNotification : SubjectNotifications) {
    this.dialog.open(SubjectNotificationsDialog, { 
      width: '650px', 
      data: subjectNotification ,
    });
  }

  save(){
    if(this.formCreateSilabus.valid){
        this.subservice.update(this.formCreateSilabus.value.id, this.formCreateSilabus.value).subscribe(rank => { this.about(); });
        this.ngxNotificationMsgService.open({
          status: NgxNotificationStatusMsg.SUCCESS,
          header: 'Silabus edit',
          messages: ['You have successfully edited silabus for subject ' + this.subject],
          
        });
    }
  }

  rateStudent(studentServiceID: number, subjectID: number) {
    const ids = {
      'studentServiceID': studentServiceID,
      'subjectID': subjectID
    }
    this.router.navigate(['ratestudent', { objectForChange: JSON.stringify(ids) }]);
  }

  about(){
    this.formCreateSilabus.get("id")?.setValue(null);
    this.formCreateSilabus.reset();
    this.router.navigate(['professor']);
  }

  loadStudents(){
    if(this.subjectID != -1) { // Ako postoji subject ucitavamo njegove studente
      this.students = [];
      this.followSubjectService.getAllUserForSubject(this.subjectID).subscribe(students => { 
        console.log(students);
        for(let id of students){
          this.studentServiceService.getSubjectsByID(id).subscribe((studentService : StudentService[]) => { 
            for(let student of studentService) { 
              this.students.push(student); 
              this.setDataSource();
            } 
        });
        }
      }); 
    }
  }

  setNotificationsData(id: number){
    this.subjectServiceNotifications.getBySubjectId(id).subscribe(x => { 
    this.dataSourceNotifications = new MatTableDataSource(x.slice());
    this.dataSourceNotifications.sort = this.sort;
    });
  }

  setDataSource(){
    this.dataSource = new MatTableDataSource(this.students);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.searchFormInit();
    this.dataSource.filterPredicate = this.getFilterPredicate();
  }

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchNameSurname: new FormControl(''),
      searchIndexNo: new FormControl(''),
      searchDate: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: StudentService, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchSearchNameSurname = filterArray[0];
      const searchSearchIndexNo = filterArray[1];
      const searchSearchDate = filterArray[2];
      
      const matchFilter = [];

      // Fetch data from row
      const columnSearchSearchNameSurname = row.student.student.user.name + " " + row.student.student.user.surname;
      const columnSearchSearchIndexNo = row.student.indexNo;
      const columnSearchSearchDate = row.student.dateOfEntry;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchSearchNameSurname.toLowerCase().includes(searchSearchNameSurname);
      const customFilter2 = columnSearchSearchIndexNo.toString().toLowerCase().includes(searchSearchIndexNo);
      const customFilter3 = columnSearchSearchDate.toString().toLowerCase().includes(searchSearchDate);

      // push boolean values into array
      matchFilter.push(customFilter1);
      matchFilter.push(customFilter2);
      matchFilter.push(customFilter3);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchNameSurname').value;
    const tt = this.searchForm.get('searchIndexNo').value;
    const sd = this.searchForm.get('searchDate').value;

    this.searchNameSurname = sn === null ? '' : sn;
    this.searchIndexNo = tt === null ? '' : tt;
    this.searchDate = sd === null ? '' : sd;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchNameSurname + '$' + this.searchIndexNo + '$' + this.searchDate;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchNameSurname = '';
    this.searchIndexNo = '';
    this.searchDate = '';
    this.searchForm.reset();
    this.loadStudents();
  }


  details(){
    this.kliknut = true;
    window.scrollTo(0, document.body.scrollHeight);
  }

  hide(){
    this.kliknut = false;
  }
  
}

//Notification dialog component
@Component({
  selector: 'subject-notification-dialog',
  templateUrl: 'subject-notification-dialog.html',
  styleUrls: ['./about-subject.component.css']
})
export class SubjectNotificationsDialog implements OnInit {

  role: string = null;

  constructor(public dialogRef: MatDialogRef<SubjectNotificationsDialog>, private subjectNotificationsService: SubjectNotificationsService, 
    @Inject(MAT_DIALOG_DATA) public subjectNotification: SubjectNotifications, private tokenStorageService: TokenStorageService) {}

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes("ROLE_ADMINISTRATOR") || user.roles.includes("ROLE_PROFESSOR")) { this.role = "ROLE_ACCESS"; };
    this.formCreateEditNotification.patchValue(this.subjectNotification);
  }

  formCreateEditNotification : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "naziv" : new FormControl(null, [Validators.required]),
    "opis" : new FormControl(null, [Validators.required]),
    "subject" : new FormControl(null, [Validators.required]),
    "professor" : new FormControl(null, [Validators.required])
  });

  closeDialog(): void {
    this.dialogRef.close();
  }

  submit() {
    if(this.formCreateEditNotification.valid){
      if(this.formCreateEditNotification.value.id == null){
        this.subjectNotificationsService.create(this.formCreateEditNotification.value).subscribe();
      } else {
        this.subjectNotificationsService.update(this.formCreateEditNotification.value.id, this.formCreateEditNotification.value).subscribe();
      }
      this.closeDialog();
    }
  }
}