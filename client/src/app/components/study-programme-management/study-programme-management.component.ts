import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { StudyProgramme } from 'src/app/model/study-programme';
import { StudyProgrammeService } from 'src/app/service/study-programme/study-programme.service';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { FormControl, FormGroup } from '@angular/forms';
import { SelectionModel } from '@angular/cdk/collections';
import { Faculty } from 'src/app/model/faculty';
import { FacultyService } from 'src/app/service/faculty/faculty.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-study-programme-management',
  templateUrl: './study-programme-management.component.html',
  styleUrls: ['./study-programme-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class StudyProgrammeManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'name', 'director', 'faculty', 'description', 'actions']; //for now
  dataSource: MatTableDataSource<StudyProgramme>;
  columnsToDisplayWithExpand = [...this.displayedColumns];
  expandedElement: StudyProgramme | null;
  TotalRow: number;  
  selection = new SelectionModel <StudyProgramme> (true, []);  
  // studyProgrammes : StudyProgramme[] = []

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchName" : new FormControl(null),
    "searchDirector" : new FormControl(null),
    "searchFaculty" : new FormControl(null)
  });
  searchName = '';
  searchDirector = '';
  searchFaculty = '';

  facultys : Faculty[] = [];

  username : string = null;
  role : String = null;

  constructor(private studyProgrammeService: StudyProgrammeService, private router: Router, private facultyService: FacultyService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
    this.username = user.username;

    this.refreshTable();
    this.facultyService.getAll().subscribe((objects : Faculty[]) => { this.facultys = objects; });
  }

  addNewStudyProgramme(){
    this.router.navigate(['studyprogrammetable/createeditstudyprogramme']);
  }
  
  selectForChange(studyProgramme: StudyProgramme){
    this.router.navigate(['studyprogrammetable/createeditstudyprogramme', {studyProgrammeForChange: JSON.stringify(studyProgramme)}]);
  }
  
  refreshTable(){
    if(this.role == "ROLE_ADMINISTRATOR"){
      this.studyProgrammeService.getAll().subscribe((objects : StudyProgramme[]) => { this.setObjectsInTable(objects) });
    } else {
      this.studyProgrammeService.getByUsername(this.username).subscribe((objects : StudyProgramme[]) => { this.setObjectsInTable(objects) });
    }
  }

  setObjectsInTable(objects : StudyProgramme[]){
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
  checkboxLabel(row: StudyProgramme): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.studyProgrammeService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchName: new FormControl(''),
      searchDirector: new FormControl(''),
      searchFaculty: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: StudyProgramme, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchName = filterArray[0];
      const searchDirector = filterArray[1];
      const searchFaculty = filterArray[2];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchName = row.name;
      const columnSearchDirector = row.director.user.name + " " + row.director.user.surname;
      const columnSearchFaculty = row.faculty.name;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchName.toLowerCase().includes(searchName);
      const customFilter2 = columnSearchDirector.toString().toLowerCase().includes(searchDirector);
      const customFilter3 = columnSearchFaculty.toString().toLowerCase().includes(searchFaculty);

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
    const sn = this.searchForm.get('searchName').value;
    const tt = this.searchForm.get('searchDirector').value;
    const sf = this.searchForm.get('searchFaculty').value;

    this.searchName = sn === null ? '' : sn;
    this.searchDirector = tt === null ? '' : tt;
    this.searchFaculty = sf === null ? '' : sf;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchName + '$' + this.searchDirector + '$' + this.searchFaculty;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchName = '';
    this.searchDirector = '';
    this.searchFaculty = '';
    this.searchForm.reset();
    this.refreshTable();
  }
}
