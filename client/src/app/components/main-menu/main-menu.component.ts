import { Component, Inject, OnInit } from '@angular/core';
import { SubjectNotifications } from 'src/app/model/subject-notifications';
import { FollowService } from 'src/app/service/follow-sub/follow.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SubjectNotificationsDialog } from './board-moderator/about-subject/about-subject.component';
import { StudentService } from 'src/app/service/student/student.service';
import { Student } from 'src/app/model/student';
import { ForumService } from 'src/app/service/forum/forum/forum.service';
import { Forum } from 'src/app/model/forum/forum';
import { Podforum } from 'src/app/model/forum/podforum';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit {

  private roles: string[];
  isLoggedIn = false;

  roleAdmin = false;
  roleProfessorOrAdmin = false;
  roleProfessorOnly = false;
  roleStudent = false;

  username: string;

  subjectNotifications: SubjectNotifications[] = [];
  student : Student = null;

  forum : Forum = null;
  
  constructor(private tokenStorageService: TokenStorageService, private folloWSubjectService: FollowService, public dialog: MatDialog, private studentService: StudentService,
    private forumService: ForumService, private router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    this.forumService.getForum().subscribe(forum => { this.forum = forum; });

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      //Dobavljanje novih obavestenja za predmet i provera da li su vec procitana
      if(user.roles.includes('ROLE_STUDENT')) { 
        this.folloWSubjectService.getSubjects(user.id).subscribe(subject => { 
          let subjectNotifications : SubjectNotifications[] = [];
          for(let i of subject) {  for(let x of i.subjects) { for(let j of x.subjectNotifications) { subjectNotifications.push(j); } } };
          this.studentService.getByUsername(user.username).subscribe(student => { 
            this.student = student;
            this.subjectNotifications = [].concat(
              subjectNotifications.filter(notification1 =>  student.subjectNotifications.every(notification2 => notification1['id'] !== notification2['id'])),
              student.subjectNotifications.filter(notification2 => subjectNotifications.every(notification1 => notification2['id'] !== notification1['id'])));
          });
        });
      }
      ////////////////////////////////////////////////////////////////////////////
      
      this.roleAdmin = this.roles.includes('ROLE_ADMINISTRATOR');
      this.roleProfessorOrAdmin = this.roles.includes('ROLE_PROFESSOR') || this.roles.includes('ROLE_ADMINISTRATOR');
      this.roleProfessorOnly = this.roles.includes('ROLE_PROFESSOR');
      this.roleStudent = this.roles.includes('ROLE_STUDENT');

      this.username = user.username;

      this.openNav();
      this.closeNav();
      
      
      var dropdown = document.getElementsByClassName("dropdown-btn");
      var i;

    for (i = 0; i < dropdown.length; i++) {
        dropdown[i].addEventListener("click", function() {
          this.classList.toggle("active");
          var dropdownContent = this.nextElementSibling;
          if (dropdownContent.style.display === "block") {
            dropdownContent.style.display = "none";
          } else {
        dropdownContent.style.display = "block";
    }
      });
    }
    }
  }

  openNav() {
    document.getElementById("mySidebar").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
  }
  
  /* Set the width of the sidebar to 0 and the left margin of the page content to 0 */
  closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
  }

  readNotifications(notifications: SubjectNotifications){
    this.subjectNotifications.forEach((value, index) => { if(value['id'] == notifications['id']) this.subjectNotifications.splice(index,1); }); //Izbacujemo iz liste notifikaciju koju smo otvorili
    this.student.subjectNotifications.push(notifications);
    this.studentService.readNotification(this.student).subscribe();
    this.dialog.open(SubjectNotificationsDialog, { 
      width: '650px', 
      data: notifications
    });
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
