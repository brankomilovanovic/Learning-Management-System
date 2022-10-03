import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ERole } from 'src/app/model/role';
import { FacultyService } from 'src/app/service/faculty/faculty.service';
import { ProfessorService } from 'src/app/service/professor/professor.service';
import { RoleService } from 'src/app/service/role/role.service';
import { StudentService } from 'src/app/service/student/student.service';
import { UniversityService } from 'src/app/service/university/university.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})

export class BoardAdminComponent implements OnInit {
  
  countUsers : number = 0;
  countProfessors : number = 0;
  countStudents : number = 0;
  countAdministrators : number = 0;
  countUniversities : number = 0;
  countFaculty : number = 0;

  constructor(private router : Router, private userService: UserService, private professorService: ProfessorService, private studentService: StudentService,
    private uni: UniversityService, private facultyService: FacultyService, private roleService: RoleService) {
    this.userService.countUser("", "", "").subscribe((x : number) => { this.countUsers = x }); 
    this.professorService.getAll().subscribe(x => { this.countProfessors = x.length }); 
    this.studentService.getAll().subscribe(x => { this.countStudents = x.length });
    this.uni.getAll().subscribe(x=> { this.countUniversities = x.length });  
    this.facultyService.getAll().subscribe(x=> { this.countFaculty = x.length });  
    this.roleService.countAdministrator().subscribe((x: number) => { this.countAdministrators = x; } ); 
  }

  ngOnInit(): void {
    
  }

    
}

