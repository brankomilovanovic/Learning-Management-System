import { SelectionModel } from '@angular/cdk/collections';
import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { EvaluationKnowledge } from 'src/app/model/evaluation-knowledge';
import { SubjectRealization } from 'src/app/model/subject-realization';
import { TypeEvaluation } from 'src/app/model/type-evaluation';
import { EvaluationKnowledgeService } from 'src/app/service/evaluation-knowledge/evaluation-knowledge.service';
import { SubjectRealizationService } from 'src/app/service/subject-realization/subject-realization.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { TypeEvaluationService } from 'src/app/service/type-evaluation/type-evaluation.service';

@Component({
  selector: 'app-evaluation-knowledge-management',
  templateUrl: './evaluation-knowledge-management.component.html',
  styleUrls: ['./evaluation-knowledge-management.component.css']
})
export class EvaluationKnowledgeManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'id', 'vremePocetka', 'vremeKraja', 'bodovi', 'topic', 'typeEvaluation', 'evaluationInstrument', 'subject', 'actions'];
  dataSource: MatTableDataSource<EvaluationKnowledge>;
  TotalRow: number;  
  selection = new SelectionModel <EvaluationKnowledge> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchTipEvaluacije" : new FormControl(null),
    "searchSubject" : new FormControl(null)
  });
  searchTipEvaluacije = '';
  searchSubject = '';

  role : string = null;
  username : string = null;
  typeEvaluations : TypeEvaluation[] = [];
  subjectsRealizations : SubjectRealization[] = [];

  constructor(private evaluationKnowledgeService: EvaluationKnowledgeService, private router: Router, private tokenStorageService: TokenStorageService,
    private typeEvaluationService : TypeEvaluationService, private subjectRealizationService: SubjectRealizationService) { }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }
    this.username = user.username;

    this.refreshTable();
  }

  addNew(){
    this.router.navigate(['evaluationknowledgetable/createeditevaluationknowledge']);
  }

  refreshTable(){
    let objects : EvaluationKnowledge[] = [];
    if(this.role != "ROLE_ADMINISTRATOR"){
      this.subjectRealizationService.getByUsername(this.username).subscribe((objects : SubjectRealization[]) => { this.subjectsRealizations = objects; });
      this.evaluationKnowledgeService.getAll().subscribe((evaluationKnowledges : EvaluationKnowledge[]) => { 
        this.subjectRealizationService.getByUsername(this.username).subscribe((subjectRealizations: SubjectRealization[]) => { 
          for(let evaluationKnowledge of evaluationKnowledges) {
            for(let subjectRealization of subjectRealizations){
              if(evaluationKnowledge.subjectRealization.subject.id == subjectRealization.subject.id){
                objects.push(evaluationKnowledge);
              }
            }
          }
          this.setObjectsInTable(objects);
        });

      });
    } else {
      this.evaluationKnowledgeService.getAll().subscribe((objects : EvaluationKnowledge[]) => { this.setObjectsInTable(objects) });
      this.subjectRealizationService.getAll().subscribe((objects : SubjectRealization[]) => { this.subjectsRealizations = objects; });
    }
    this.typeEvaluationService.getAll().subscribe((objects : TypeEvaluation[]) => { this.typeEvaluations = objects; });
  }

  setObjectsInTable(objects : EvaluationKnowledge[]){
    this.dataSource = new MatTableDataSource(objects.slice());
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.searchFormInit();
    this.dataSource.filterPredicate = this.getFilterPredicate();
  }

  selectForChange(object: EvaluationKnowledge){
    this.router.navigate(['evaluationknowledgetable/createeditevaluationknowledge', {objectForChange: JSON.stringify(object)}]);
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
  checkboxLabel(row: EvaluationKnowledge): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.evaluationKnowledgeService.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchTipEvaluacije: new FormControl(''),
      searchSubject: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: EvaluationKnowledge, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchTipEvaluacije = filterArray[0];
      const searchSubject = filterArray[1];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchTipEvaluacije = row.typeEvaluation.tipEvaluacije;
      const columnSearchSubject = row.subjectRealization.subject.naziv;

      // verify fetching data by our searching values
      const customFilter1 = columnSearchTipEvaluacije.toLowerCase().includes(searchTipEvaluacije);
      const customFilter2 = columnSearchSubject.toLowerCase().includes(searchSubject);

      // push boolean values into array
      matchFilter.push(customFilter1);
      matchFilter.push(customFilter2);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchTipEvaluacije').value;
    const tt = this.searchForm.get('searchSubject').value;

    this.searchTipEvaluacije = sn === null ? '' : sn;
    this.searchSubject = tt === null ? '' : tt;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchTipEvaluacije + '$' + this.searchSubject;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchTipEvaluacije = '';
    this.searchSubject = '';
    this.searchForm.reset();
    this.refreshTable();
  }

}
