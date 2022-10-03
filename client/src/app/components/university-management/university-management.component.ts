import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { University } from 'src/app/model/university';
import { UniversityService} from 'src/app/service/university/university.service';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { SelectionModel } from '@angular/cdk/collections';
import { FormControl, FormGroup } from '@angular/forms';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-university',
  templateUrl: './university-management.component.html',
  styleUrls: ['./university-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class UniversityManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'name', 'headmaster', 'address', 'contactDetails', 'establishmentDate', 'actions']; //for now
  dataSource: MatTableDataSource<University>;
  columnsToDisplayWithExpand = [...this.displayedColumns];
  expandedElement: University | null;
  TotalRow: number; 
  selection = new SelectionModel <University> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchName" : new FormControl(null),
    "searchDean" : new FormControl(null)
  });
  searchName = '';
  searchDean = '';

  username : string = null;
  role : String = null;
  
  constructor(private universityService : UniversityService, private router: Router, private tokenStorageService: TokenStorageService) { }
  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
    this.username = user.username;

    this.refreshTable();
  }

  addNewUniversity(){
    this.router.navigate(['universitytable/createedituniversity']);
  }
  
  selectForChange(university: University){
    this.router.navigate(['universitytable/createedituniversity', {universityForChange: JSON.stringify(university)}]);
  }

  refreshTable(){
    if(this.role == "ROLE_ADMINISTRATOR"){
      this.universityService.getAll().subscribe((faculty : University[]) => { this.setObjectsInTable(faculty) });
    } else {
      this.universityService.getAllByUsername(this.username).subscribe((faculty : University[]) => { this.setObjectsInTable(faculty) });
    }
  }

  setObjectsInTable(objects : University[]){
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
  checkboxLabel(row: University): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.universityService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchName: new FormControl(''),
      searchDean: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: University, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchName = filterArray[0];
      const searchDean = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnsearchName = row.name;
      const columnsearchDean = row.headmaster.user.name + " " + row.headmaster.user.surname;

      // verify fetching data by our searching values
      const customFilter1 = columnsearchName.toLowerCase().includes(searchName);
      const customFilter2 = columnsearchDean.toString().toLowerCase().includes(searchDean);

      // push boolean values into array
      matchFilter.push(customFilter1);
      matchFilter.push(customFilter2);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchName').value;
    const tt = this.searchForm.get('searchDean').value;

    this.searchName = sn === null ? '' : sn;
    this.searchDean = tt === null ? '' : tt;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchName + '$' + this.searchDean;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchName = '';
    this.searchDean = '';
    this.searchForm.reset();
    this.refreshTable();
  }
}
