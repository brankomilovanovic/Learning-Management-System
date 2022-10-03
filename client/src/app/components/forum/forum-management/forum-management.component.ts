import { animate, state, style, transition, trigger } from '@angular/animations';
import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Forum } from 'src/app/model/forum/forum';
import { Podforum } from 'src/app/model/forum/podforum';
import { ForumService } from 'src/app/service/forum/forum/forum.service';
import { PodforumService } from 'src/app/service/forum/podforum/podforum.service';

@Component({
  selector: 'app-forum-management',
  templateUrl: './forum-management.component.html',
  styleUrls: ['./forum-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class ForumManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'naziv', 'opis', 'actions'];
  dataSource: MatTableDataSource<Podforum>;
  columnsToDisplayWithExpand = [...this.displayedColumns];
  expandedElement: Podforum | null;
  TotalRow: number;  
  selection = new SelectionModel <Podforum> (true, []);  

  forum : Forum = null;

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "search" : new FormControl(null)
  });
  search = '';

  constructor(private forumService: ForumService, private podforumService: PodforumService, private router: Router) { }

  ngOnInit(): void {
    this.refreshTable();
  }

  add(){
    this.router.navigate(['forumtable/createeditforum']);
  }

  selectForChange(object: Podforum){
    this.router.navigate(['forumtable/createeditforum', {objectForChange: JSON.stringify(object)}]);
  }

  refreshTable(){
    this.forumService.getForum().subscribe(forum => { this.forum = forum; });
    this.podforumService.getAll().subscribe(podforum => {
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(podforum.slice());
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.searchFormInit();
      this.dataSource.filterPredicate = this.getFilterPredicate();
    });
  }

  updateVidljivost(vidljivost: boolean){
    this.forumService.updateVidljivost(vidljivost).subscribe(x => { this.forum.javni = vidljivost; })
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
  checkboxLabel(row: Podforum): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.podforumService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      search: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: Podforum, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const search = filterArray[0];

      const matchFilter = [];

      // Fetch data from row
      const columnSearch = row.naziv;

      // verify fetching data by our searching values
      const customFilterSN = columnSearch.toLowerCase().includes(search);

      // push boolean values into array
      matchFilter.push(customFilterSN);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('search').value;

    this.search = sn === null ? '' : sn;

    // create string of our searching values and split if by '$'
    const filterValue = this.search;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.search = '';
    this.searchForm.reset();
    this.refreshTable();
  }


}
