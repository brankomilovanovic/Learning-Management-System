import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ClassTime } from 'src/app/model/class-time';
import { ClassTimeService } from 'src/app/service/class-time/class-time.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { SelectionModel } from '@angular/cdk/collections';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TypeOfTeaching } from 'src/app/model/type-of-teaching';
import { TypeOfTeachingService } from 'src/app/service/type-of-teaching/type-of-teaching.service';

@Component({
  selector: 'app-class-time',
  templateUrl: './class-time.component.html',
  styleUrls: ['./class-time.component.css']
})
export class ClassTimeComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  TotalRow: number;  
  displayedColumns: string[] = ['select', 'id', 'vremePocetka', 'vremeKraja', 'topic', 'typeOfTeaching', 'subjectRealization', 'actions'];
  dataSource: MatTableDataSource<ClassTime>;
  selection = new SelectionModel <ClassTime> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchSubjectName" : new FormControl(null),
    "searchTypeOfTeaching" : new FormControl(null)
  });
  searchTypeOfTeaching = '';
  searchSubjectName = '';
  typeOfTeachings: TypeOfTeaching[] = [];

  role : string = null;
  username : string = null;

  constructor(private classTimeService: ClassTimeService, private router: Router, private tokenStorageService: TokenStorageService,
    private typeOfTeachingService: TypeOfTeachingService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
    this.username = user.username;

    this.refreshTable();
    this.typeOfTeachingService.getAll().subscribe((objects: TypeOfTeaching[]) => { this.typeOfTeachings = objects; });
  }
  
  addNew(){
    this.router.navigate(['classtimetable/createeditclasstime']);
  }

  refreshTable(){
    if(this.role == "ROLE_ADMINISTRATOR"){
      this.classTimeService.getAll().subscribe((classTimes : ClassTime[]) => { this.setObjectsInTable(classTimes) });
    } else {
      this.classTimeService.getByUsername(this.username).subscribe((classTimes : ClassTime[]) => { this.setObjectsInTable(classTimes) });
    }
  }

  setObjectsInTable(objects : ClassTime[]){
    this.dataSource = new MatTableDataSource(objects.slice());
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.searchFormInit();
    this.dataSource.filterPredicate = this.getFilterPredicate();
  }

  selectForChange(object: ClassTime){
    this.router.navigate(['classtimetable/createeditclasstime', { objectForChange: JSON.stringify(object) } ]);
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
  checkboxLabel(row: ClassTime): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.classTimeService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchSubjectName: new FormControl(''),
      searchTypeOfTeaching: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: ClassTime, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchSubjectName = filterArray[0];
      const searchTypeOfTeaching = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchSubjectName = row.subjectRealization.subject.naziv;
      const columnSearchTypeOfTeaching = row.typeOfTeaching.naziv;
      
      // verify fetching data by our searching values
      const customFilterSN = columnSearchSubjectName.toLowerCase().includes(searchSubjectName);
      const customFilterTT = columnSearchTypeOfTeaching.toString().toLowerCase().includes(searchTypeOfTeaching);

      // push boolean values into array
      matchFilter.push(customFilterSN);
      matchFilter.push(customFilterTT);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchSubjectName').value;
    const tt = this.searchForm.get('searchTypeOfTeaching').value;

    this.searchSubjectName = sn === null ? '' : sn;
    this.searchTypeOfTeaching = tt === null ? '' : tt;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchSubjectName + '$' + this.searchTypeOfTeaching;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchSubjectName = '';
    this.searchTypeOfTeaching = '';
    this.searchForm.reset();
    this.refreshTable();
  }
}
