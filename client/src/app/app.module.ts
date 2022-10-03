import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatStepperModule } from '@angular/material/stepper';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MainMenuComponent } from './components/main-menu/main-menu.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatSortModule } from '@angular/material/sort';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { NgxNotificationMsgModule } from 'ngx-notification-msg';
import { MatDialogModule } from '@angular/material/dialog';
import { MatBadgeModule } from '@angular/material/badge';

import { AppComponent } from './app.component';
import { authInterceptorProviders } from './helper/auth.interceptor';
import { LoginComponent } from './components/home/login/login.component';
import { RegisterComponent } from './components/home/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/main-menu/profile/profile.component';
import { BoardAdminComponent } from './components/main-menu/board-admin/board-admin.component';
import { BoardModeratorComponent } from './components/main-menu/board-moderator/board-moderator.component';
import { BoardUserComponent } from './components/main-menu/board-user/board-user.component';
import { UsersManagementComponent } from './components/users-management/users-management-table.component';
import { TypeRanksManagementComponent } from './components/type-ranks-management/type-ranks-management.component';
import { CreateEditTypeRanksComponent } from './components/type-ranks-management/create-edit-type-ranks/create-edit-type-ranks.component';
import { ScientificAreaManagementComponent } from './components/scientific-area-management/scientific-area-management.component';
import { CreateEditScientificAreaComponent } from './components/scientific-area-management/create-edit-scientific-area/create-edit-scientific-area.component';
import { CreateEditUserComponent } from './components/users-management/create-edit-user/create-edit-user.component';
import { RankManagementComponent } from './components/rank-management/rank-management.component';
import { CreateEditRankComponent } from './components/rank-management/create-edit-rank/create-edit-rank.component';
import { CreateEditFacultyComponent } from './components/faculty-management/create-edit-faculty/create-edit-faculty.component';
import { FacultyManagementComponent } from './components/faculty-management/faculty-management.component';
import { TopicManagementComponent } from './components/topic-management/topic-management.component';
import { CraeteEditTopicComponent } from './components/topic-management/create-edit-topic/craete-edit-topic.component';
import { SubjectManagementComponent } from './components/subject-management/subject-management.component';
import { CreateEditSubjectComponent } from './components/subject-management/create-edit-subject/create-edit-subject.component';

import { YearManagementComponent } from './components/year-management/year-management.component';
import { CreateEditYearComponent } from './components/year-management/create-edit-year/create-edit-year.component';
import { StudentOnTheYearManagementComponent } from './components/student-on-the-year-management/student-on-the-year-management.component';
import { CreateEditStudentOnTheYearComponent } from './components/student-on-the-year-management/create-edit-student-on-the-year/create-edit-student-on-the-year.component';
import { StudyProgrammeManagementComponent } from './components/study-programme-management/study-programme-management.component';
import { CreateEditStudyProgrammeComponent } from './components/study-programme-management/create-edit-study-programme/create-edit-study-programme.component';
import { StudentServiceManagementComponent } from './components/student-service-management/student-service-management.component';
import { CreateEditStudentServiceComponent } from './components/student-service-management/create-edit-student-service/create-edit-student-service.component';
import { TeacherOnRealizationManagementComponent } from './components/teacher-on-realization-management/teacher-on-realization-management.component';
import { CreateEditTeacherOnRealizationComponent } from './components/teacher-on-realization-management/create-edit-teacher-on-realization/create-edit-teacher-on-realization.component';
import { SubjectRealizationManagementComponent } from './components/subject-realization-management/subject-realization-management.component';
import { CreateEditSubjectRealizationComponent } from './components/subject-realization-management/create-edit-subject-realization/create-edit-subject-realization.component';
import { EvaluationKnowledgeManagementComponent } from './components/evaluation-knowledge-management/evaluation-knowledge-management.component';
import { CreateEditEvaluationKnowledgeComponent } from './components/evaluation-knowledge-management/create-edit-evaluation-knowledge/create-edit-evaluation-knowledge.component';
import { FileManagementComponent } from './components/file-management/file-management.component';
import { CreateEditFileComponent } from './components/file-management/create-edit-file/create-edit-file.component';
import { EvaluationInstrumentComponent } from './components/evaluation-instrument-management/evaluation-instrument.component';
import { CreateEditEvaluationInstrumentComponent } from './components/evaluation-instrument-management/create-edit-evaluation-instrument/create-edit-evaluation-instrument.component';
import { UniversityManagementComponent } from './components/university-management/university-management.component';
import { CreateEditUniversityComponent } from './components/university-management/create-edit-university/create-edit-university.component';
import { EducationGoalManagementComponent } from './components/education-goal-management/education-goal-management.component';
import { CreateEditEducationGoalComponent } from './components/education-goal-management/create-edit-education-goal/create-edit-education-goal.component';
import { CreateEditClassTimeComponent } from './components/class-time-management/create-edit-class-time/create-edit-class-time.component';
import { ClassTimeComponent } from './components/class-time-management/class-time.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { RouterModule } from '@angular/router';
import { AboutSubjectComponent, SubjectNotificationsDialog } from './components/main-menu/board-moderator/about-subject/about-subject.component';
import { TeachingMaterialComponent } from './components/teaching-material/teaching-material.component';
import { CreateEditTeachingMaterialComponent } from './components/teaching-material/create-edit-teaching-material/create-edit-teaching-material.component';
import { PickSubjectComponent } from './components/main-menu/board-user/pick-subject/pick-subject.component';
import { SubjectDetailsComponent } from './components/main-menu/board-user/subject-details/subject-details.component';
import { RateStudentComponent } from './components/main-menu/board-moderator/about-subject/rate-student/rate-student.component';
import { DetailedComponent } from './components/home/details/detailed/detailed.component';
import { PodforumComponent } from './components/forum/podforum/podforum.component';
import { TemaComponent } from './components/forum/tema/tema.component';
import { ForumManagementComponent } from './components/forum/forum-management/forum-management.component';
import { ForumComponent } from './components/forum/forum/forum.component';
import { CreateEditTemaComponent } from './components/forum/tema/create-edit-tema/create-edit-tema.component';
import { CreateEditForumComponent } from './components/forum/forum-management/create-edit-forum/create-edit-forum.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    CreateEditUserComponent,
    MainMenuComponent,
    UsersManagementComponent,
    TypeRanksManagementComponent,
    CreateEditTypeRanksComponent,
    ScientificAreaManagementComponent,
    CreateEditScientificAreaComponent,
    RankManagementComponent,
    CreateEditRankComponent,
    CreateEditUserComponent,
    YearManagementComponent,
    CreateEditYearComponent,
    StudyProgrammeManagementComponent,
    CreateEditStudyProgrammeComponent,
    StudentOnTheYearManagementComponent,
    CreateEditStudentOnTheYearComponent,
    CreateEditFacultyComponent,
    FacultyManagementComponent,
    TopicManagementComponent,
    CraeteEditTopicComponent,
    SubjectManagementComponent,
    CreateEditSubjectComponent,
    StudentServiceManagementComponent,
    CreateEditStudentServiceComponent,
    TeacherOnRealizationManagementComponent,
    CreateEditTeacherOnRealizationComponent,
    SubjectRealizationManagementComponent,
    CreateEditSubjectRealizationComponent,
    UniversityManagementComponent,
    CreateEditUniversityComponent,
    EvaluationKnowledgeManagementComponent,
    CreateEditEvaluationKnowledgeComponent,
    FileManagementComponent,
    CreateEditFileComponent,
    EvaluationInstrumentComponent,
    CreateEditEvaluationInstrumentComponent,
    EducationGoalManagementComponent,
    CreateEditEducationGoalComponent,
    ClassTimeComponent,
    CreateEditClassTimeComponent,
    AboutSubjectComponent,
    TeachingMaterialComponent,
    CreateEditTeachingMaterialComponent,
    PickSubjectComponent,
    SubjectDetailsComponent,
    RateStudentComponent,
    SubjectNotificationsDialog,
    DetailedComponent,
    PodforumComponent,
    TemaComponent,
    ForumManagementComponent,
    ForumComponent,
    CreateEditTemaComponent,
    CreateEditForumComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatTableModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatStepperModule,
    MatDatepickerModule,
    MatMenuModule,
    MatSortModule,
    MatExpansionModule,
    MatAutocompleteModule,
    MatCardModule,
    MatCheckboxModule,
    RouterModule,
    NgxNotificationMsgModule,
    MatDialogModule,
    MatBadgeModule,
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
