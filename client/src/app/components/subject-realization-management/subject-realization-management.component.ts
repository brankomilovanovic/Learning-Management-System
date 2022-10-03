import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';

import { SubjectRealization } from 'src/app/model/subject-realization';
import { TypeOfTeaching } from 'src/app/model/type-of-teaching';
import { SubjectRealizationService } from 'src/app/service/subject-realization/subject-realization.service';
import { TypeOfTeachingService } from 'src/app/service/type-of-teaching/type-of-teaching.service';


@Component({
  selector: 'app-subject-realization-management',
  templateUrl: './subject-realization-management.component.html',
  styleUrls: ['./subject-realization-management.component.css']
})

export class SubjectRealizationManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'subject', 'teacherOnRealization', 'typeOfTeaching', 'actions'];
  dataSource: MatTableDataSource<SubjectRealization>;
  TotalRow: number;  
  selection = new SelectionModel <SubjectRealization> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchPredmet" : new FormControl(null),
    "searchProfesor" : new FormControl(null),
    "searchTypeOfTeaching" : new FormControl(null)
  });
  searchPredmet = '';
  searchProfesor = '';  
  searchTypeOfTeaching = '';
  typeOfTeachings: TypeOfTeaching[] = [];

  constructor(private subjectRealizationService: SubjectRealizationService, private router: Router, private typeOfTeachingService: TypeOfTeachingService) { }

  ngOnInit(): void {
    this.refreshTable();
    this.typeOfTeachingService.getAll().subscribe(objects => { this.typeOfTeachings = objects; });
  }

  addNew(){
    this.router.navigate(['subjectrealizationtable/createeditsubjectrealization']);
  }

  selectForChange(object: SubjectRealizationService){
    this.router.navigate(['subjectrealizationtable/createeditsubjectrealization', {objectForChange: JSON.stringify(object)}]);
  }

  refreshTable(){
    this.subjectRealizationService.getAll().subscribe((objects : SubjectRealization[]) => {
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
  checkboxLabel(row: SubjectRealization): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.subjectRealizationService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchPredmet: new FormControl(''),
      searchProfesor: new FormControl(''),
      searchTypeOfTeaching: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: SubjectRealization, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchPredmet = filterArray[0];
      const searchProfesor = filterArray[1];
      const searchTypeOfTeaching = filterArray[2];

      const matchFilter = [];

      // Fetch data from row
      const columnsearchPredmet = row.subject.naziv;
      const columnsearchProfesor = row.teacherOnRealization.professor.user.name + " " + row.teacherOnRealization.professor.user.username;
      var columnSearchTypeOfTeaching: string = "";
      for(let i in row.teacherOnRealization.typeOfTeaching){
        columnSearchTypeOfTeaching += row.teacherOnRealization.typeOfTeaching[i].naziv + "\n";
      }
      
      // verify fetching data by our searching values
      const customFilter1 = columnsearchPredmet.toLowerCase().includes(searchPredmet);
      const customFilter2 = columnsearchProfesor.toLowerCase().includes(searchProfesor);
      const customFilter3 = columnSearchTypeOfTeaching.toString().toLowerCase().includes(searchTypeOfTeaching);

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
    const sn = this.searchForm.get('searchPredmet').value;
    const tt = this.searchForm.get('searchProfesor').value;
    const tot = this.searchForm.get('searchTypeOfTeaching').value;

    this.searchPredmet = sn === null ? '' : sn;
    this.searchProfesor = tt === null ? '' : tt;
    this.searchTypeOfTeaching = tot === null ? '' : tot;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchPredmet + '$' + this.searchProfesor + '$' + this.searchTypeOfTeaching;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchPredmet = '';
    this.searchProfesor = '';
    this.searchTypeOfTeaching = '';
    this.searchForm.reset();
    this.refreshTable();
  }
}
