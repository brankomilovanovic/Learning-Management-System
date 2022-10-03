import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { StudentServiceService } from 'src/app/service/student-service/student-service.service';
import { StudentService } from 'src/app/model/student-service';
import { SelectionModel } from '@angular/cdk/collections';
import { FormControl, FormGroup } from '@angular/forms';
import { StudyProgrammeService } from 'src/app/service/study-programme/study-programme.service';
import { StudyProgramme } from 'src/app/model/study-programme';
import { UserService } from 'src/app/service/user/user.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { User } from 'src/app/model/user';
import { StudentTests } from 'src/app/model/student-tests';
import { HistoryService } from 'src/app/service/history/history.service';
import { Student } from 'src/app/model/student';
import { History } from 'src/app/model/history';
import { StudentTestService } from 'src/app/service/student-test/student-test.service';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import * as FileSaver from 'file-saver';
import beautify from "xml-beautifier";
import * as converter from 'xml-js';

@Component({
  selector: 'app-student-service-management',
  templateUrl: './student-service-management.component.html',
  styleUrls: ['./student-service-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class StudentServiceManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  show: boolean=false;

  displayedColumns: string[] = ['select', 'id', 'studentontheyear', 'student', 'programme', 'actions'];
  dataSource: MatTableDataSource<StudentService>;
  columnsToDisplayWithExpand = [...this.displayedColumns];
  expandedElement: StudentService | null;
  TotalRow: number;  
  selection = new SelectionModel <StudentService> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchIndexNo" : new FormControl(null),
    "searchStudyProgramme" : new FormControl(null)
  });
  searchIndexNo = '';
  searchStudyProgramme = '';

  programmes : StudyProgramme[] = [];

  service : StudentService[] = []  
  history: History[]=[]
  tests : StudentTests[]=[];

  user: User;
  test: StudentTests;
  ser: StudentService;

  constructor(private token: TokenStorageService, private sp: StudyProgrammeService, private router: Router,
    public us: UserService, private h: HistoryService, private s: StudentServiceService, private testService: StudentTestService) { }

    formHistory : FormGroup = new FormGroup({
      "id" : new FormControl(null),
      "user" : new FormControl(null),
      "tests" : new FormControl(null),
      "enroll" : new FormControl(null),
    });
  
  ngOnInit(): void {
    this.refreshTable();
    this.sp.getAll().subscribe((sp: StudyProgramme[]) => { this.programmes = sp; });   
    // const currentUser = this.token.getUser();

    this.h.getAll().subscribe(s => { 
      this.history = s 
       console.log("ISTOIJA SVIH STUDENATA: ", s)
    });

    this.s.getAll().subscribe(s => {
      this.service = s;
      // console.log("STUDENT SERVICE: ",this.service)
    });

    this.testService.getAll().subscribe(u => {
      this.tests = u;
      // console.log("POLAGANJA: ",this.tests)
    });

    // this.us.getOne(currentUser.username).subscribe((user : User) => {
    //   console.log(user)
    // });
  }

  saveInHistory(){
    for(let s of this.service){
      this.user = s.student.student.user;
      this.ser = s;
    }
    for(let r of this.tests){
      this.test = r;
    }

    this.formHistory.get("user").setValue(this.user)
    console.log(this.user)
    this.formHistory.get("tests").setValue(this.test)
    console.log(this.test)
    this.formHistory.get("enroll").setValue(this.ser)
    console.log(this.ser)

    this.h.create(this.formHistory.value).subscribe(x=> {
     console.log(x);
   });
 }

  addStudent(){
    this.router.navigate(['studentservicetable/createeditstudentservice']);
  }

  selectForChange(year: StudentService){
    this.router.navigate(['studentservicetable/createeditstudentservice', {studentForChange: JSON.stringify(year)}]);
  }

  deleteYear(id: number) {
    this.s.delete(id).subscribe(rank => { this.refreshTable(); })
  };

  refreshTable(){
    this.s.getAll().subscribe(s => {
      console.log(s);
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(s.slice());
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.searchFormInit();
      this.dataSource.filterPredicate = this.getFilterPredicate();
    });
  }

   /** Whether the number of selected elements matches the total number of rows. */  
  isAllSelected() {  
    const numSelected = this.selection.selected.length;  
    const numRows = !!this.dataSource && this.dataSource.data.length;  
    return numSelected === numRows;  
  }  

  /** Selects all rows if they are not all selected; otherwise clear selection. */  
  masterToggle() {  
    this.isAllSelected() ? this.selection.clear() : this.dataSource.data.forEach(r => this.selection.select(r));  
  }    

  /** The label for the checkbox on the passed row */  
  checkboxLabel(row: StudentService): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.s.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchIndexNo: new FormControl(''),
      searchStudyProgramme: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: StudentService, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchIndexNo = filterArray[0];
      const searchStudyProgramme = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchIndexNo = row.student.indexNo;
      const columnSearchStudyProgramme = row.studyProgrammes.name;

      // verify fetching data by our searching values
      const customFilterSN = columnSearchIndexNo.toLowerCase().includes(searchIndexNo);
      const customFilterTT = columnSearchStudyProgramme.toString().toLowerCase().includes(searchStudyProgramme);

      // push boolean values into array
      matchFilter.push(customFilterSN);
      matchFilter.push(customFilterTT);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchIndexNo').value;
    const tt = this.searchForm.get('searchStudyProgramme').value;

    this.searchIndexNo = sn === null ? '' : sn;
    this.searchStudyProgramme = tt === null ? '' : tt;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchIndexNo + '$' + this.searchStudyProgramme;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchIndexNo = '';
    this.searchStudyProgramme = '';
    this.searchForm.reset();
    this.refreshTable();
  }

  exportPDF(){

    this.show = true;
  
    setTimeout(()=>{                           
      const doc = new jsPDF('p', 'px', 'a4');
     autoTable(doc, {html:'#content'});
     doc.save('Professor_list' + '.pdf');
  }, 10);
  setTimeout(()=>{                           
    this.show = false;
  }, 10);
  }

  exportXML() {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    window?.Buffer = window?.Buffer || require("buffer").Buffer
    var student = this.selection.selected;
    
    if (student.length > 0) {  
      if(student.length > 1){
        var students = {student};
      

       
        var str = JSON.stringify({students});

        var xml = beautify(str);

        
        var outputXml = converter.json2xml(xml, {compact: true, spaces: 4});

            var fileToExport = new Blob([outputXml], {type: "text/xml"});
            window.open(URL.createObjectURL(fileToExport));
            FileSaver.saveAs(fileToExport, "students.xml");
      }
      else{
        var str = JSON.stringify({student});

        var xml = beautify(str);
        
        var outputXml = converter.json2xml(xml, {compact: true, spaces: 4});

            var fileToExport = new Blob([outputXml], {type: "text/xml"});
            window.open(URL.createObjectURL(fileToExport));
            FileSaver.saveAs(fileToExport, student[0].student.student.user.name + student[0].student.student.user.surname +".xml");
      }
      

          
       
    } else { alert("Error: Select at least one row!"); }  

}

}
