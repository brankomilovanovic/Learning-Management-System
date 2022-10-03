import { Component, OnInit } from '@angular/core';
import { TypeRanks } from 'src/app/model/type-ranks';
import { TypeRanksService } from 'src/app/service/type-ranks/type-ranks.service';
import { MatPaginator } from '@angular/material/paginator';
import { ViewChild} from '@angular/core';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-type-ranks-management',
  templateUrl: './type-ranks-management.component.html',
  styleUrls: ['./type-ranks-management.component.css']
})
export class TypeRanksManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'name', 'actions'];
  dataSource: MatTableDataSource<TypeRanks>;
  selection = new SelectionModel <TypeRanks> (true, []);  

  constructor(private typeRanksService: TypeRanksService, private router: Router) { }

  ngOnInit(): void {
    this.refreshTable();
  }

  addNewRank(){
    this.router.navigate(['typerankstable/createeditrank']);
  }

  selectForChange(typeRank: TypeRanks){
    this.router.navigate(['typerankstable/createeditrank', {typeRankForChange: JSON.stringify(typeRank)}]);
  }

  deleteLogical() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { 
          object.active = false;
          this.typeRanksService.update(object.id, object).subscribe(rank => { this.refreshTable(); this.selection.clear(); });
        }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  return(typeRank: TypeRanks) {
    typeRank.active = true;
    this.typeRanksService.update(typeRank.id, typeRank).subscribe(rank => { this.refreshTable(); });
  };

  deletePhysically(id: number) {
    this.typeRanksService.delete(id).subscribe(x => { this.refreshTable(); })
  };

  refreshTable(){
    this.typeRanksService.getAll().subscribe(typeRank => {
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(typeRank);
      this.dataSource.filterPredicate = (ranks: TypeRanks, filterValue: string) => { 
        const datasrc = ranks['name']
        return datasrc.trim().toLowerCase().indexOf(filterValue) != -1;                
      }
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  applySearch(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
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
  checkboxLabel(row: TypeRanks): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

}
