import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ScientificArea } from 'src/app/model/scientific-area';
import { ScientificAreaService } from 'src/app/service/scientific-area/scientific-area.service';


@Component({
  selector: 'app-scientific-area-management',
  templateUrl: './scientific-area-management.component.html',
  styleUrls: ['./scientific-area-management.component.css']
})
export class ScientificAreaManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  TotalRow: number;  
  displayedColumns: string[] = ['select', 'id', 'name', 'actions'];
  dataSource: MatTableDataSource<ScientificArea>;
  selection = new SelectionModel <ScientificArea> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchName" : new FormControl(null)
  });
  searchName = '';

  constructor(private scientificAreaService: ScientificAreaService, private router: Router) { }

  ngOnInit(): void {
    this.refreshTable();
  }

  addNewScientificArea(){
    this.router.navigate(['scientificareatable/createeditscientificarea']);
  }

  selectForChange(scientificArea: ScientificArea){
    this.router.navigate(['scientificareatable/createeditscientificarea', {scientificAreaForChange: JSON.stringify(scientificArea)}]);
  }

  deleteLogical() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { 
          object.active = false;
          this.scientificAreaService.update(object.id, object).subscribe(rank => { this.resetSearch(); this.selection.clear(); });
        }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  return(scientificArea: ScientificArea) {
    scientificArea.active = true;
    this.scientificAreaService.update(scientificArea.id, scientificArea).subscribe(rank => { this.refreshTable(); });
  };

  deletePhysically(id: number) {
    this.scientificAreaService.delete(id).subscribe(x => { this.refreshTable(); })
  };

  refreshTable(){
    this.scientificAreaService.getAll().subscribe(scientificArea => {
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(scientificArea.slice());
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
  checkboxLabel(row: ScientificArea): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchName: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: ScientificArea, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchName = filterArray[0];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchName = row.name;

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
