import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { EvaluationInstrument } from 'src/app/model/evaluation-instrument';
import { EvaluationKnowledge } from 'src/app/model/evaluation-knowledge';
import { EvaluationInstrumentService } from 'src/app/service/evaluation-instrument/evaluation-instrument.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-evaluation-instrument',
  templateUrl: './evaluation-instrument.component.html',
  styleUrls: ['./evaluation-instrument.component.css']
})
export class EvaluationInstrumentComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'tipTestiranja', 'file', 'actions'];
  dataSource: MatTableDataSource<EvaluationInstrument>;
  TotalRow: number;  
  selection = new SelectionModel <EvaluationInstrument> (true, []);  

  role : string = null;

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchTipTestiranja" : new FormControl(null),
    "searchFile" : new FormControl(null)
  });
  searchTipTestiranja = '';
  searchFile = '';

  constructor(private evaluationInstrumentService: EvaluationInstrumentService, private tokenStorageService: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }

    this.refreshTable();
  }

  addNew(){
    this.router.navigate(['evaluationinstrumenttable/createeditevaluationinstrument']);
  }

  refreshTable(){
    this.evaluationInstrumentService.getAll().subscribe(objects => {
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(objects);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;

      this.searchFormInit();
      this.dataSource.filterPredicate = this.getFilterPredicate();
    });
  }

  selectForChange(object: EvaluationKnowledge){
    this.router.navigate(['evaluationinstrumenttable/createeditevaluationinstrument', {objectForChange: JSON.stringify(object)}]);
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
  checkboxLabel(row: EvaluationInstrument): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.evaluationInstrumentService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchTipTestiranja: new FormControl(''),
      searchFile: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: EvaluationInstrument, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchTipTestiranja = filterArray[0];
      const searchFile = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchTipTestiranja = row.tipTestiranja;
      const columnSearchFile = row.file.opis;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchTipTestiranja.toLowerCase().includes(searchTipTestiranja);
      const customFilter2 = columnSearchFile.toLowerCase().includes(searchFile);

      // push boolean values into array
      matchFilter.push(customFilter1);
      matchFilter.push(customFilter2);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchTipTestiranja').value;
    const tt = this.searchForm.get('searchFile').value;

    this.searchTipTestiranja = sn === null ? '' : sn;
    this.searchFile = tt === null ? '' : tt;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchTipTestiranja + '$' + this.searchFile;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchTipTestiranja = '';
    this.searchFile = '';
    this.searchForm.reset();
    this.refreshTable();
  }

}
