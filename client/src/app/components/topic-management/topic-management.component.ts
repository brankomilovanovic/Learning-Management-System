import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';

import { Topic } from 'src/app/model/topic';
import { TypeOfTopic } from 'src/app/model/type-of-topic';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { TopicService } from 'src/app/service/topic/topic.service';
import { TypeOfTopicService } from 'src/app/service/type-of-topic/type-of-topic.service';

@Component({
  selector: 'app-topic-management',
  templateUrl: './topic-management.component.html',
  styleUrls: ['./topic-management.component.css']
})
export class TopicManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'opis', 'topicType', 'actions'];
  dataSource: MatTableDataSource<Topic>;
  TotalRow: number;  
  selection = new SelectionModel <Topic> (true, []);  

  role : String = null;

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchOpis" : new FormControl(null),
    "searchTopicTypes" : new FormControl(null)
  });
  searchOpis = '';  
  searchTopicTypes = '';

  topicTypes : TypeOfTopic[] = [];
  
  constructor(private topicService: TopicService, private tokenStorageService: TokenStorageService, private router: Router, private typeOfTopicService: TypeOfTopicService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }

    this.refreshTable();
    this.typeOfTopicService.getAll().subscribe(topics => { this.topicTypes = topics; });
  }

  addNew(){
    this.router.navigate(['topictable/createedittopic']);
  }

  selectForChange(topic: Topic){
    this.router.navigate(['topictable/createedittopic', {topicForChange: JSON.stringify(topic)}]);
  }

  refreshTable(){
    this.topicService.getAll().subscribe(topics => {
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(topics.slice());
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
  checkboxLabel(row: Topic): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.topicService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchOpis: new FormControl(''),
      searchTopicTypes: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: Topic, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchOpis = filterArray[0];
      const searchTopicTypes = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnsearchOpis = row.opis;
      const columnsearchTopicTypes = row.topicType.naziv;
      
      // verify fetching data by our searching values
      const customFilter2 = columnsearchOpis.toLowerCase().includes(searchOpis);
      const customFilter3 = columnsearchTopicTypes.toString().toLowerCase().includes(searchTopicTypes);

      // push boolean values into array
      matchFilter.push(customFilter2);
      matchFilter.push(customFilter3);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const tt = this.searchForm.get('searchOpis').value;
    const tot = this.searchForm.get('searchTopicTypes').value;

    this.searchOpis = tt === null ? '' : tt;
    this.searchTopicTypes = tot === null ? '' : tot;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchOpis + '$' + this.searchTopicTypes;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchOpis = '';
    this.searchTopicTypes = '';
    this.searchForm.reset();
    this.refreshTable();
  }

}
