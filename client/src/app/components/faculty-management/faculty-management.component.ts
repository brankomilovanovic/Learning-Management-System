import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Faculty } from 'src/app/model/faculty';
import { FacultyService } from 'src/app/service/faculty/faculty.service';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { FormControl, FormGroup } from '@angular/forms';
import { SelectionModel } from '@angular/cdk/collections';
import { University } from 'src/app/model/university';
import { UniversityService } from 'src/app/service/university/university.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-faculty-management',
  templateUrl: './faculty-management.component.html',
  styleUrls: ['./faculty-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class FacultyManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  TotalRow: number;  
  displayedColumns: string[] = ['select', 'id', 'name', 'university', 'dean', 'address', 'contactDetails', 'actions'];
  dataSource: MatTableDataSource<Faculty>;
  columnsToDisplayWithExpand = [...this.displayedColumns];
  expandedElement: Faculty | null;
  selection = new SelectionModel <Faculty> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchFacultyName" : new FormControl(null),
    "searchUniversity" : new FormControl(null)
  });
  searchFacultyName = '';
  searchUniversity = '';

  username : String = null;
  role : String = null;

  universitys : University[] = []

  constructor(private facultyService: FacultyService, private router: Router, private universityService: UniversityService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
    this.username = user.username;

    this.refreshTable();
    this.universityService.getAll().subscribe((objects: University[]) => { this.universitys = objects; });
  }

  addNewFaculty(){
    this.router.navigate(['facultytable/createeditfaculty']);
  }
  
  
  selectForChange(faculty: Faculty){
    this.router.navigate(['facultytable/createeditfaculty', {facultyForChange: JSON.stringify(faculty)}]);
  }

  refreshTable(){
    if(this.role == "ROLE_ADMINISTRATOR"){
      this.facultyService.getAll().subscribe((faculty : Faculty[]) => { this.setObjectsInTable(faculty) });
    } else {
      this.facultyService.getByUsername(this.username).subscribe((faculty : Faculty[]) => { this.setObjectsInTable(faculty) });
    }
  }

  setObjectsInTable(objects : Faculty[]){
    this.dataSource = new MatTableDataSource(objects.slice());
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
  checkboxLabel(row: Faculty): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.facultyService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchFacultyName: new FormControl(''),
      searchUniversity: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: Faculty, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchFacultyName = filterArray[0];
      const searchUniversity = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchFacultyName = row.name;
      const columnSearchUniversity = row.university.name;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchFacultyName.toLowerCase().includes(searchFacultyName);
      const customFilter2 = columnSearchUniversity.toString().toLowerCase().includes(searchUniversity);

      // push boolean values into array
      matchFilter.push(customFilter1);
      matchFilter.push(customFilter2);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchFacultyName').value;
    const tt = this.searchForm.get('searchUniversity').value;

    this.searchFacultyName = sn === null ? '' : sn;
    this.searchUniversity = tt === null ? '' : tt;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchFacultyName + '$' + this.searchUniversity;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchFacultyName = '';
    this.searchUniversity = '';
    this.searchForm.reset();
    this.refreshTable();
  }
}
