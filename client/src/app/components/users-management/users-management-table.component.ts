import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { History } from '../../model/history';
import { UserService } from 'src/app/service/user/user.service';
import { HistoryService } from 'src/app/service/history/history.service';
import { Router } from '@angular/router';
import { ViewChild} from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';
import { ERole, Role } from 'src/app/model/role';
import { RoleService } from 'src/app/service/role/role.service';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { SelectionModel } from '@angular/cdk/collections';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { StudentServiceService } from 'src/app/service/student-service/student-service.service';
import { StudentTestService } from 'src/app/service/student-test/student-test.service';
import { StudentTests } from 'src/app/model/student-tests';
import { StudentService } from 'src/app/model/student-service';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { StudentOnTheYearService } from 'src/app/service/student-on-the-year/student-on-the-year.service';
import { StudentOnTheYear } from 'src/app/model/student-on-the-year';
import { Adress } from 'src/app/model/adress';

@Component({
  selector: 'app-users-management-table',
  templateUrl: './users-management-table.component.html',
  styleUrls: ['./users-management-table.component.css']
})
export class UsersManagementComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  users : User[] = []
  roles : Role[] = []
  us : User;
  kliknut : boolean = false;

  role : string = null;

  pageStart : boolean = true; // ovo mora zbog autocomplete, sam se pozove kad udjem na stranicu pa pravi gresku searchu, zato sto jos nema datasource

  //Autocomplete
  myControl = new FormControl();
  filteredOptions: Observable<User[]> | undefined;

  displayedColumns: string[] = ['select', 'id', 'name', 'surname', 'username', 'email', 'roles', 'actions'];
  dataSource: MatTableDataSource<User>;
  TotalRow: number;  
  selection = new SelectionModel <User> (true, []);  

  //Paras for get all users from server side
  pageNo : number = 0;
  total : number = 0;
  pageSize : number = 5;
  pageSizeOptions : number[] = [5, 10, 20];
  sortBy : string = "id";

  //SEARCH
  searchName : string = "";
  searchSurname : string = "";
  searchRoleID : string = "";

  history: History[]=[]
  tests : StudentTests[]=[];
  service : StudentService[]=[];

  student: StudentOnTheYear;
  test: StudentTests[]=[];
  e: StudentTests[]=[];

  adresa: any;
  city: String;
  steet: String;
  country: String;

  bodovi: number = 0;
  ocena: number = 0;

  upisi: StudentService[]=[];

  constructor(private userService: UserService, private router: Router, private roleService: RoleService, private tokenStorageService: TokenStorageService
    , private h: HistoryService, private s: StudentServiceService, private testService: StudentTestService, 
    private sy: StudentOnTheYearService) {}

  formHistory : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "user" : new FormControl(null),
    "tests" : new FormControl(null),
    "enroll" : new FormControl(null),
  });

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();

    if(user.roles.includes('ROLE_ADMINISTRATOR')) { this.role = "ROLE_ADMINISTRATOR"; }

    this.roleService.getAll().subscribe(role => { this.roles = role; });

    //autocomplete search
    this.filteredOptions = this.myControl.valueChanges.pipe(startWith(''), map(value => this._filter(value || '')),);

    if(this.role != "ROLE_ADMINISTRATOR") { this.roleService.getByName("ROLE_STUDENT").subscribe(x => { this.searchRoleID = x.id.toString(); this.refreshTable(); }); } // Ako korisnik nije administrator prikazati mu samo studente
    else { this.refreshTable(); } // Ako je korisnik administrator samo uradimo refresh table
  }

  selectStudentDetails(user: User){ //ZA ISTORIJU STUDIRANJA
    this.kliknut = true;
    this.test = [];
    this.city= null;
    this.country= null;
    this.steet= null;

    if(this.bodovi > 0){
      this.bodovi=0;
    }

    this.sy.getSubjectsByID(user.id).subscribe(x=>{
      this.upisi = x;
      //console.log("UPISI",x);
    })

    this.sy.getOne(user.id).subscribe(x=>{
      this.student = x;
      let u = x;
      for(let r of u.studentTests){
        this.test.push(r);
        this.city = String(x.student.address.city);
        this.country = String(x.student.address.country);
        this.steet = String(x.student.address.streetAndNumber);

        //console.log("ADRESA", this.adresa)
        
    }for(let b of u.studentTests){
      let testovi = [];
      testovi.push(b);
      
      this.bodovi += b.ocena / this.test.length;
    }
      
      
    });
  }
  
  hide(){
    this.kliknut = false;
  }

  selectForChange(user: User){
    this.router.navigate(['userstable/createedituser', {userForChange: JSON.stringify(user)}]);
  }

  addNewUser(){
    this.router.navigate(['userstable/createedituser']);
  }

  page(event: Event){
    this.pageNo = event['pageIndex'];
    this.pageSize = event['pageSize'];
    this.refreshTable();
  }

  refreshTable(){
    this.userService.countUser(this.searchName, this.searchSurname, this.searchRoleID).subscribe((x : number) => { this.total = x } )
    this.userService.getAll(this.pageNo, this.pageSize, this.sortBy, this.searchName, this.searchSurname, this.searchRoleID).subscribe(newUsers => {
      this.users = newUsers;
      console.log(newUsers);
      //Data Table Data Source and pagination with dynamic data
      this.dataSource = new MatTableDataSource(newUsers.slice());
      this.dataSource.sort = this.sort;
    })
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
  checkboxLabel(row: User): string {  
    if (!row) {  
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;  
    }  
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;  
  }  

  delete() {  
    const numSelected = this.selection.selected;
    if (numSelected.length > 0) {  
      if (confirm("Are you sure to delete items?")) {  
        for(let object of numSelected) { this.userService.delete(object.id).subscribe(x => { this.refreshTable(); this.selection.clear(); }); }
      }  
    } else { alert("Error: Select at least one row!"); }  
  } 

  ///// AUTOCOMPLETE /////
  displayUser(user: string) {
    return user ? user: undefined;
  }  

  private _filter(value: string): User[] {
    if(this.pageStart == true){
      this.pageStart = false;
    } else {
      let filterValues : string[] = value.toLowerCase().split(" ");
      this.searchName = filterValues[0]; // Dodeljujemo ima iz prvog indeksa nakon splitovanja
      
      if(filterValues[1] != undefined && filterValues[1] != '') { this.searchSurname = filterValues[1]; } //Ako na drugom indeksu ne postoji nista onda ne dodeljujemo surname
      else { this.searchSurname = ''; } //Kada brisemo search ako ne postoji nista na drugom indeksu setovati opet prazan string
      
      this.refreshTable();
    }
    const filterValue = value.toString().toLowerCase();
    return this.users.filter((user: { name: { toString: () => string }, surname: { toString: () => string } }) => 
      user.name.toString().toLowerCase().includes(filterValue) || user.surname.toString().toLowerCase().includes(filterValue)
    );
  }

  selectedRole(role: string){
    this.searchRoleID = role;
    this.refreshTable();
  }

  resetSearch(){
    if(this.role == "ROLE_ADMINISTRATOR") { this.searchRoleID = ''; } // samo ako je korisnik admin resetujemo role, inace ako nije admin ostavljamo role koji je na pocetku definisan
    this.myControl.setValue('');
    this.searchName = '';
    this.searchSurname = '';
    this.refreshTable();
  }

  //JUST FOR TEST
  generate300Users() {
    this.userService.generate300Users().subscribe(x => { this.refreshTable(); })
  };
}
