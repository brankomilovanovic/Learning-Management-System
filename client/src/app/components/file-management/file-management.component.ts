import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { File } from 'src/app/model/file';
import { FileService } from 'src/app/service/file/file.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';


@Component({
  selector: 'app-file-management',
  templateUrl: './file-management.component.html',
  styleUrls: ['./file-management.component.css']
})
export class FileManagementComponent implements OnInit {
  
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'opis', 'url', 'actions'];
  dataSource: MatTableDataSource<File>;
  selection = new SelectionModel <File> (true, []);  

  role : string = null;

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchOpis" : new FormControl(null)
  });
  searchOpis = '';

  constructor(private fileService: FileService, private tokenStorageService: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }

    this.refreshTable();
  }

  addNew(){
    this.router.navigate(['filetable/createeditfile']);
  }

  refreshTable(){
    this.fileService.getAll().subscribe(objects => {
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(objects);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.searchFormInit();
      this.dataSource.filterPredicate = this.getFilterPredicate();
    });
  }

  selectForChange(object: File){
    this.router.navigate(['filetable/createeditfile', {objectForChange: JSON.stringify(object)}]);
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
  checkboxLabel(row: File): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.fileService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchOpis: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: File, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchOpis = filterArray[0];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchOpis = row.opis;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchOpis.toLowerCase().includes(searchOpis);

      // push boolean values into array
      matchFilter.push(customFilter1);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchOpis').value;

    this.searchOpis = sn === null ? '' : sn;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchOpis;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchOpis = '';
    this.searchForm.reset();
    this.refreshTable();
  }

}
