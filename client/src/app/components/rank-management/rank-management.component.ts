import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Rank } from 'src/app/model/rank';
import { ScientificArea } from 'src/app/model/scientific-area';
import { TypeRanks } from 'src/app/model/type-ranks';
import { RankService } from 'src/app/service/rank/rank.service';
import { ScientificAreaService } from 'src/app/service/scientific-area/scientific-area.service';
import { TypeRanksService } from 'src/app/service/type-ranks/type-ranks.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { SelectionModel } from '@angular/cdk/collections';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-rank-management',
  templateUrl: './rank-management.component.html',
  styleUrls: ['./rank-management.component.css']
})
export class RankManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'electionDate', 'terminationDate', 'typeRanks', 'scientificArea', 'professor', 'actions'];
  dataSource: MatTableDataSource<Rank>;
  TotalRow: number;  
  selection = new SelectionModel <Rank> (true, []);  

 //SEARCH
 searchForm : FormGroup = new FormGroup({
  "searchScientificArea" : new FormControl(null),
  "searchTypeRank" : new FormControl(null),
  "searchProfessorName" : new FormControl(null)
  });
  searchScientificArea = '';
  searchTypeRank = '';
  searchProfessorName = '';

  username : string = null;
  role : String = "ROLE_ADMINISTRATOR";

  scientificAreas : ScientificArea[] = [];
  typeRanks : TypeRanks[] = [];

  constructor(private rankService: RankService, private router: Router, private scientificAreaService: ScientificAreaService, private typeRanksService: TypeRanksService,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_PROFESSOR')){
      this.role = "ROLE_PROFESSOR"
      this.username = user.username;
    }
    this.refreshTable();
  }

  addNewRank(){
    this.router.navigate(['rankstable/createeditrank']);
  }

  selectForChange(rank: Rank){
    this.router.navigate(['rankstable/createeditrank', {rankForChange: JSON.stringify(rank)}]);
  }

  refreshTable(){
    if(this.username == null){
      this.rankService.getAll().subscribe((ranks : Rank[]) => { this.setObjectsInTable(ranks); });
    } else {
      this.rankService.getByUsername(this.username).subscribe((ranks : Rank[]) => { this.setObjectsInTable(ranks); });
    }
  }

  setObjectsInTable(objects : Rank[]){
    this.scientificAreas = [];
    this.typeRanks = [];
    if(this.role == "ROLE_PROFESSOR") {
      for(let rank of objects){
        this.scientificAreaService.getAll().subscribe((scientificAreas : ScientificArea[]) => { 
          for(let scientificArea of scientificAreas){
            if(scientificArea.id == rank.scientificArea.id){ this.scientificAreas.push(scientificArea); }
          }
        });
        this.typeRanksService.getAll().subscribe((typeRanks : TypeRanks[]) => { 
          for(let typeRank of typeRanks){
            if(typeRank.id == rank.typeRanks.id){ this.typeRanks.push(typeRank); }
          }
        });
      }
    } else {
      this.scientificAreaService.getAll().subscribe((scientificAreas : ScientificArea[]) => { this.scientificAreas = scientificAreas; });
      this.typeRanksService.getAll().subscribe((typeRanks : TypeRanks[]) => { this.typeRanks = typeRanks; } );
    }
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
  checkboxLabel(row: Rank): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.rankService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchScientificArea: new FormControl(''),
      searchTypeRank: new FormControl(''),
      searchProfessorName: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: Rank, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchScientificArea = filterArray[0];
      const searchTypeRank = filterArray[1];
      const searchProfessorName = filterArray[2];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchScientificArea = row.scientificArea.name;
      const columnSearchTypeRank = row.typeRanks.name;
      const columnSearchProfessorName = row.professor.user.name + " " + row.professor.user.surname;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchScientificArea.toLowerCase().includes(searchScientificArea);
      const customFilter2 = columnSearchTypeRank.toString().toLowerCase().includes(searchTypeRank);
      const customFilter3 = columnSearchProfessorName.toString().toLowerCase().includes(searchProfessorName);

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
    const sn = this.searchForm.get('searchScientificArea').value;
    const tt = this.searchForm.get('searchTypeRank').value;
    const pn = this.searchForm.get('searchProfessorName').value;

    this.searchScientificArea = sn === null ? '' : sn;
    this.searchTypeRank = tt === null ? '' : tt;
    this.searchProfessorName = pn === null ? '' : pn;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchScientificArea + '$' + this.searchTypeRank + '$' + this.searchProfessorName;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchScientificArea = '';
    this.searchTypeRank = '';
    this.searchProfessorName = '';
    this.searchForm.reset();
    this.refreshTable();
  }

}
