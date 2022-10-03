import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { StudentOnTheYear } from 'src/app/model/student-on-the-year';
import { StudentOnTheYearService } from 'src/app/service/student-on-the-year/student-on-the-year.service';


@Component({
  selector: 'app-student-on-the-year-management',
  templateUrl: './student-on-the-year-management.component.html',
  styleUrls: ['./student-on-the-year-management.component.css']
})
export class StudentOnTheYearManagementComponent implements OnInit {

  show: boolean = false;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  TotalRow: number;  
  displayedColumns: string[] = ['select', 'id','indexNo', 'studentName', 'studentJmbg', 'dateOfEntry', 'actions'];
  dataSource: MatTableDataSource<StudentOnTheYear>;
  selection = new SelectionModel <StudentOnTheYear> (true, []);  

  //SEARCH
  searchForm : FormGroup = new FormGroup({
    "searchStudentName" : new FormControl(null)
  });
  searchStudentName = '';

  constructor(private router: Router, private sy: StudentOnTheYearService) { }

  ngOnInit(): void {
    this.refreshTable();
  }

  addStudenOnTheYear(){
    this.router.navigate(['studentontheyeartable/createedit']);
  }

  selectForChange(student: StudentOnTheYear){
    this.router.navigate(['studentontheyeartable/createedit', {studentForChange: JSON.stringify(student)}]);
  }

  refreshTable(){
    this.sy.getAll().subscribe(studentOnTheYear => {      
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(studentOnTheYear.slice());
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
  checkboxLabel(row: StudentOnTheYear): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.sy.delete(object.id).subscribe(x => { this.resetSearch(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  searchFormInit() {
    this.searchForm = new FormGroup({
      searchStudentName: new FormControl('')
    });
  }

  /* this method well be called for each row in table  */
  getFilterPredicate() {
    return (row: StudentOnTheYear, filters: string) => {
      // split string per '$' to array
      const filterArray = filters.split('$');
      const searchStudentName = filterArray[0];

      const matchFilter = [];

      // Fetch data from row
      const columnSearchStudentName = row.student.user.name + ' ' + row.student.user.surname;

      // verify fetching data by our searching values
      const customFilterSN = columnSearchStudentName.toLowerCase().includes(searchStudentName);

      // push boolean values into array
      matchFilter.push(customFilterSN);

      // return true if all values in array is true
      // else return false
      return matchFilter.every(Boolean);
    };
  }

  applyFilter() {
    const sn = this.searchForm.get('searchStudentName').value;

    this.searchStudentName = sn === null ? '' : sn;

    // create string of our searching values and split if by '$'
    const filterValue = this.searchStudentName;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  resetSearch(){
    this.searchStudentName = '';
    this.searchForm.reset();
    this.refreshTable();
  }

  exportPDF(){
    this.show = true;

    setTimeout(()=>{                           
      const doc = new jsPDF('p', 'px', 'a4');
     autoTable(doc, {html:'#content'});
     doc.save('Student_list' + '.pdf');
  }, 10);
  setTimeout(()=>{                           
    this.show = false;
  }, 10);
  }
}
