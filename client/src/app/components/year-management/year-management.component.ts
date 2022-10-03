import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Year } from 'src/app/model/year';

import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { YearService } from 'src/app/service/yearofstudy/year.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { FormControl, FormGroup } from '@angular/forms';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-year-management',
  templateUrl: './year-management.component.html',
  styleUrls: ['./year-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class YearManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'year','active','sp', 'actions'];
  dataSource: MatTableDataSource<Year>;
  columnsToDisplayWithExpand = [...this.displayedColumns];
  expandedElement: Year | null;
  TotalRow: number;  
  selection = new SelectionModel <Year> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchStudyProgramme" : new FormControl(null),
    "searchActive" : new FormControl(null)
  });
  searchStudyProgramme = '';  
  searchActive = '';

  constructor(private ys: YearService, private router: Router) { }

  ngOnInit(): void {
    this.refreshTable();
  }

  addYear(){
    this.router.navigate(['yeartable/createedit']);
  }

  selectForChange(year: Year){
    this.router.navigate(['yeartable/createedit', {yearForChange: JSON.stringify(year)}]);
  }


  deleteYear(id: number) {
    this.ys.delete(id).subscribe(rank => { this.refreshTable(); })
  };
  
  refreshTable(){
    this.ys.getAll().subscribe(years => {
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(years.slice());
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.searchFormInit();
      this.dataSource.filterPredicate = this.getFilterPredicate();
    });
  }
  
  return(year: Year) {
    year.active = true;
    this.ys.update(year.id, year).subscribe(rank => { this.refreshTable(); });
  };
  
  deletePhysically(id: number) {
    this.ys.delete(id).subscribe(x => { this.refreshTable(); })
  };

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
  checkboxLabel(row: Year): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { 
          object.active = false;
          this.ys.update(object.id, object).subscribe(rank => { this.resetSearch(); this.selection.clear(); });
        }  
      }
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchStudyProgramme: new FormControl(''),
      searchActive: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: Year, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchStudyProgramme = filterArray[0];
      const searchActive = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnsearchStudyProgramme = row.studyProgramme.name;
      const columnsearchActive = row.active;
      
      // verify fetching data by our searching values
      const customFilter2 = columnsearchStudyProgramme.toLowerCase().includes(searchStudyProgramme);
      const customFilter3 = columnsearchActive.toString().toLowerCase().includes(searchActive);

      // push boolean values into array
      matchFilter.push(customFilter2);
      matchFilter.push(customFilter3);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const tt = this.searchForm.get('searchStudyProgramme').value;
    const tot = this.searchForm.get('searchActive').value;

    this.searchStudyProgramme = tt === null ? '' : tt;
    this.searchActive = tot === null ? '' : tot;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchStudyProgramme + '$' + this.searchActive;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchStudyProgramme = '';
    this.searchActive = '';
    this.searchForm.reset();
    this.refreshTable();
  }

}
