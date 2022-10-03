import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subject } from 'src/app/model/subject';
import { SubjectService } from 'src/app/service/subject/subject.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { SelectionModel } from '@angular/cdk/collections';
import { FormControl, FormGroup } from '@angular/forms';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { SubjectRealization } from 'src/app/model/subject-realization';
import { SubjectRealizationService } from 'src/app/service/subject-realization/subject-realization.service';

@Component({
  selector: 'app-subject-management',
  templateUrl: './subject-management.component.html',
  styleUrls: ['./subject-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class SubjectManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'naziv', 'espb', 'obavezan', 'brojPredavanja', 'brojVezbi', 'drugiObliciNastave', 'istrazivackiRad', 'ostaliCasovi', 'actions'];
  dataSource: MatTableDataSource<Subject>;
  columnsToDisplayWithExpand = [...this.displayedColumns];
  expandedElement: Subject | null;
  TotalRow: number;  
  selection = new SelectionModel <Subject> (true, []);  

  username : String = null;
  role : String = null;
  subjects : Subject[] = [];

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchName" : new FormControl(null)
  });
  searchName = '';
  
  constructor(private subjectService: SubjectService, private subjectRealizationService: SubjectRealizationService, private router: Router, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
    this.username = user.username;

    this.refreshTable();
  }

  addNew(){
    this.router.navigate(['subjecttable/createeditsubject']);
  }

  selectForChange(subject: Subject){
    this.router.navigate(['subjecttable/createeditsubject', {subjectForChange: JSON.stringify(subject)}]);
  }

  refreshTable(){
    if(this.role == "ROLE_ADMINISTRATOR"){
      this.subjectService.getAll().subscribe((subjects : Subject[]) => { this.subjects = subjects; this.setObjectInTable(); });
    } else {
      this.subjectRealizationService.getByUsername(this.username).subscribe((value : SubjectRealization[]) => { for(let i of value) { this.subjects.push(i.subject); } this.setObjectInTable(); });
    }
    
  }

  setObjectInTable(){
    //Data Table Data Source and pagination with dynamic data
    this.dataSource = new MatTableDataSource(this.subjects.slice());
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.searchFormInit();
    this.dataSource.filterPredicate = this.getFilterPredicate();
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
  checkboxLabel(row: Subject): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.subjectService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchName: new FormControl(''),
      searchObavezan: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: Subject, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchName = filterArray[0];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchName = row.naziv;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchName.toLowerCase().includes(searchName);

      // push boolean values into array
      matchFilter.push(customFilter1);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchName').value;

    this.searchName = sn === null ? '' : sn;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchName;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchName = '';
    this.searchForm.reset();
    this.refreshTable();
  }
}
