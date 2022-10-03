import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TeachingMaterial } from 'src/app/model/teaching-material';
import { TeachingMaterialService } from 'src/app/service/teaching-material/teaching-material.service';
import { map, startWith } from 'rxjs/operators';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-teaching-material',
  templateUrl: './teaching-material.component.html',
  styleUrls: ['./teaching-material.component.css']
})
export class TeachingMaterialComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'naziv', 'godinaIzdavanja', 'autori', 'file', 'actions'];
  dataSource: MatTableDataSource<TeachingMaterial>;
  TotalRow: number;  
  selection = new SelectionModel <TeachingMaterial> (true, []);  

  //Autocomplete
  myControl = new FormControl();
  filteredOptions: Observable<string[]> | undefined;
  pageStart : boolean = true; // ovo mora zbog autocomplete, sam se pozove kad udjem na stranicu pa pravi gresku searchu, zato sto jos nema datasource
  autori : string[] = [];

  role : string = null;

 //SEARCH
 searchForm : FormGroup = new FormGroup({
  "searchNaziv" : new FormControl(null),
  "searchAutor" : new FormControl(null)
  });
  searchNaziv = '';
  searchAutor = '';

  constructor(private teachingMaterialService: TeachingMaterialService, private tokenStorageService: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
    
    this.refreshTable();
    this.teachingMaterialService.getAllAuthors().subscribe((objects : string[]) => { this.autori = objects; })

    //autocomplete
    this.filteredOptions = this.myControl.valueChanges.pipe(startWith(''), map(value => this._filter(value || '')),);
  }

  addNew(){
    this.router.navigate(['teachingmaterialtable/craeteeditteachingmaterial']);
  }

  selectForChange(object: TeachingMaterial){
    this.router.navigate(['teachingmaterialtable/craeteeditteachingmaterial', {objectForChange: JSON.stringify(object)}]);
  }

  refreshTable(){
    this.teachingMaterialService.getAll().subscribe((objects : TeachingMaterial[]) => { 
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
  checkboxLabel(row: TeachingMaterial): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.teachingMaterialService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchNaziv: new FormControl(''),
      searchAutor: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: TeachingMaterial, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchNaziv = filterArray[0];
      const searchAutor = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnsearchNaziv = row.naziv;
      const columnsearchAutor = row.autori;

      // verify fetching data by our searching values
      const customFilter1 = columnsearchNaziv.toLowerCase().includes(searchNaziv);
      const customFilter2 = columnsearchAutor.toString().toLowerCase().includes(searchAutor);

      // push boolean values into array
      matchFilter.push(customFilter1);
      matchFilter.push(customFilter2);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchNaziv').value;
    const sa = this.searchForm.get('searchAutor').value;

    this.searchNaziv = sn === null ? '' : sn;
    this.searchAutor = sa === null ? '' : sa;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchNaziv + '$' + this.searchAutor;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.myControl.setValue('');
    this.searchAutor = '';
    this.searchNaziv = '';
    this.searchForm.reset();
    this.refreshTable();
  }

  /////// AUTOCOMPLETE /////
  private _filter(value: string): string[] {
    if(this.pageStart == true){
      this.pageStart = false;
    } else {
      this.searchForm.get("searchAutor").setValue(value);
      this.applyFilter();
    }
    const filterValue = value.toLowerCase();
    return this.autori.filter(option => option.toLowerCase().includes(filterValue));
  }

}
