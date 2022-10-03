import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { animate, state, style, transition, trigger } from '@angular/animations';

import { TeacherOnRealization } from 'src/app/model/teacher-on-realization';
import { TypeOfTeaching } from 'src/app/model/type-of-teaching';
import { TeacherOnRealizationService } from 'src/app/service/teacher-on-realization/teacher-on-realization.service';
import { TypeOfTeachingService } from 'src/app/service/type-of-teaching/type-of-teaching.service';
import { FormControl, FormGroup } from '@angular/forms';
import { SelectionModel } from '@angular/cdk/collections';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import * as FileSaver from 'file-saver';
import beautify from "xml-beautifier";
import * as converter from 'xml-js';








@Component({
  selector: 'app-teacher-on-realization-management',
  templateUrl: './teacher-on-realization-management.component.html',
  styleUrls: ['./teacher-on-realization-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
// angular.module('myApp', ['ceibo.components.table.export']);

export class TeacherOnRealizationManagementComponent implements OnInit {
  
  teacher: TeacherOnRealization[]=[];
  typeOfTeachingAll: TypeOfTeaching[] = [];
  show: boolean=false;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('content', {static: true}) el!: ElementRef;

  
  displayedColumns: string[] = ['select', 'id', 'brojCasova', 'professor', 'typeOfTeaching', 'actions'];

  dataSource: MatTableDataSource<TeacherOnRealization>;
  columnsToDisplayWithExpand = [...this.displayedColumns];
  expandedElement: TeacherOnRealization | null;
  TotalRow: number;  
  selection = new SelectionModel <TeacherOnRealization> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchProfesor" : new FormControl(null),
    "searchTypeOfTeaching" : new FormControl(null)
  });
  searchProfesor = '';  
  searchTypeOfTeaching = '';
  objs: any;
  
  constructor(private teacherOnRealizationService: TeacherOnRealizationService, private router: Router, 
    private typeOfTeachingService : TypeOfTeachingService) { }

  ngOnInit(): void {
    this.displayedColumns = [...this.displayedColumns, 'actions'];
    // this.elem  = document.getElementById('akcije');
    
    this.refreshTable();
    this.typeOfTeachingService.getAll().subscribe(objects => { this.typeOfTeachingAll = objects; });
    // this.legend = document.getElementById("content");
  }

  addNew(){
    this.router.navigate(['teacheronrealizationtable/createeditteacheronrealization']);
  }

  selectForChange(object: TeacherOnRealization){
    this.router.navigate(['teacheronrealizationtable/createeditteacheronrealization', {objectForChange: JSON.stringify(object)}]);
  }

  refreshTable(){
    this.teacherOnRealizationService.getAll().subscribe(objects => {
      this.teacher = objects;
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(objects.slice());
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
  checkboxLabel(row: TeacherOnRealization): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.teacherOnRealizationService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchProfesor: new FormControl(''),
      searchTypeOfTeaching: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: TeacherOnRealization, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchProfesor = filterArray[0];
      const searchTypeOfTeaching = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnsearchProfesor = row.professor.user.name + " " + row.professor.user.username;
      var columnSearchTypeOfTeaching: string = "";
      for(let i in row.typeOfTeaching){
        columnSearchTypeOfTeaching += row.typeOfTeaching[i].naziv + "\n";
      }
      
      // verify fetching data by our searching values
      const customFilter2 = columnsearchProfesor.toLowerCase().includes(searchProfesor);
      const customFilter3 = columnSearchTypeOfTeaching.toString().toLowerCase().includes(searchTypeOfTeaching);

      // push boolean values into array
      matchFilter.push(customFilter2);
      matchFilter.push(customFilter3);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const tt = this.searchForm.get('searchProfesor').value;
    const tot = this.searchForm.get('searchTypeOfTeaching').value;

    this.searchProfesor = tt === null ? '' : tt;
    this.searchTypeOfTeaching = tot === null ? '' : tot;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchProfesor + '$' + this.searchTypeOfTeaching;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchProfesor = '';
    this.searchTypeOfTeaching = '';
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
      var teacherOnRealization = this.selection.selected;
      
      if (teacherOnRealization.length > 0) {  
        if(teacherOnRealization.length > 1){
          var teachersOnRealization = {teacherOnRealization};
        

         
          var str = JSON.stringify({teachersOnRealization});
  
          var xml = beautify(str);
  
          
          var outputXml = converter.json2xml(xml, {compact: true, spaces: 4});
  
              var fileToExport = new Blob([outputXml], {type: "text/xml"});
              window.open(URL.createObjectURL(fileToExport));
              FileSaver.saveAs(fileToExport, "professors.xml");
        }
        else{
          var str = JSON.stringify({teacherOnRealization});
  
          var xml = beautify(str);
          
          var outputXml = converter.json2xml(xml, {compact: true, spaces: 4});
  
              var fileToExport = new Blob([outputXml], {type: "text/xml"});
              window.open(URL.createObjectURL(fileToExport));
              FileSaver.saveAs(fileToExport, teacherOnRealization[0].professor.user.name + teacherOnRealization[0].professor.user.surname +".xml");
        }
        

            
         
      } else { alert("Error: Select at least one row!"); }  

  }


  
  }



