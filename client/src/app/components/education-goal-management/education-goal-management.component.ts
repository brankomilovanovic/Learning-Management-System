import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { EducationGoal } from 'src/app/model/education-goal';
import { EducationGoalService } from 'src/app/service/education-goal/education-goal.service';
import { Router } from '@angular/router';
import { SelectionModel } from '@angular/cdk/collections';
import { FormControl, FormGroup } from '@angular/forms';
import { Topic } from 'src/app/model/topic';
import { TopicService } from 'src/app/service/topic/topic.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-education-goal-management',
  templateUrl: './education-goal-management.component.html',
  styleUrls: ['./education-goal-management.component.css']
})
export class EducationGoalManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'opis', 'topic', 'actions'];
  dataSource: MatTableDataSource<EducationGoal>;
  TotalRow: number;  
  selection = new SelectionModel <EducationGoal> (true, []);  

  role : string = "ROLE_ADMINISTRATOR";

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchOpis" : new FormControl(null),
    "searchTopic" : new FormControl(null)
  });
  searchTopic = '';
  searchOpis = '';

  constructor(private educationGoalService: EducationGoalService, private tokenStorageService: TokenStorageService, private topicService: TopicService, private router: Router) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_PROFESSOR')){
      this.role = "ROLE_PROFESSOR";
    } 
    this.refreshTable();
  }

  addNew(){
    this.router.navigate(['educationgoaltable/createediteducationgoal']);
  }

  refreshTable(){
    this.educationGoalService.getAll().subscribe(objects => {
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(objects);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      
      this.searchFormInit();
      this.dataSource.filterPredicate = this.getFilterPredicate();
    });
  }

  selectForChange(object: File){
    this.router.navigate(['educationgoaltable/createediteducationgoal', {objectForChange: JSON.stringify(object)}]);
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
  checkboxLabel(row: EducationGoal): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.educationGoalService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchOpis: new FormControl(''),
      searchTopic: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: EducationGoal, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchOpis = filterArray[0];
      const searchTopic = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchOpis = row.opis;
      const columnSearchTopic = row.topic.opis;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchOpis.toLowerCase().includes(searchOpis);
      const customFilter2 = columnSearchTopic.toLowerCase().includes(searchTopic);

      // push boolean values into array
      matchFilter.push(customFilter1);
      matchFilter.push(customFilter2);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchOpis').value;
    const tt = this.searchForm.get('searchTopic').value;

    this.searchOpis = sn === null ? '' : sn;
    this.searchTopic = tt === null ? '' : tt;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchOpis + '$' + this.searchTopic;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchTopic = '';
    this.searchOpis = '';
    this.searchForm.reset();
    this.refreshTable();
  }

}
