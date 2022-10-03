import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './components/home/login/login.component';
import { RegisterComponent } from './components/home/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/main-menu/profile/profile.component';
import { BoardAdminComponent } from './components/main-menu/board-admin/board-admin.component';
import { BoardModeratorComponent } from './components/main-menu/board-moderator/board-moderator.component';
import { BoardUserComponent } from './components/main-menu/board-user/board-user.component';
import { PickSubjectComponent } from './components/main-menu/board-user/pick-subject/pick-subject.component';
import { CreateEditUserComponent } from './components/users-management/create-edit-user/create-edit-user.component';
import { UsersManagementComponent } from './components/users-management/users-management-table.component';
import { TypeRanksManagementComponent } from './components/type-ranks-management/type-ranks-management.component';
import { CreateEditTypeRanksComponent } from './components/type-ranks-management/create-edit-type-ranks/create-edit-type-ranks.component';
import { ScientificAreaManagementComponent } from './components/scientific-area-management/scientific-area-management.component';
import { CreateEditScientificAreaComponent } from './components/scientific-area-management/create-edit-scientific-area/create-edit-scientific-area.component';
import { RankManagementComponent } from './components/rank-management/rank-management.component';
import { CreateEditRankComponent } from './components/rank-management/create-edit-rank/create-edit-rank.component';
import { YearManagementComponent } from './components/year-management/year-management.component';
import { CreateEditYearComponent } from './components/year-management/create-edit-year/create-edit-year.component';
import { StudyProgrammeManagementComponent } from './components/study-programme-management/study-programme-management.component';
import { CreateEditStudyProgrammeComponent } from './components/study-programme-management/create-edit-study-programme/create-edit-study-programme.component';
import { StudentOnTheYearManagementComponent } from './components/student-on-the-year-management/student-on-the-year-management.component';
import { CreateEditStudentOnTheYearComponent } from './components/student-on-the-year-management/create-edit-student-on-the-year/create-edit-student-on-the-year.component';

import { FacultyManagementComponent } from './components/faculty-management/faculty-management.component';
import { CreateEditFacultyComponent } from './components/faculty-management/create-edit-faculty/create-edit-faculty.component';
import { TopicManagementComponent } from './components/topic-management/topic-management.component';
import { CraeteEditTopicComponent } from './components/topic-management/create-edit-topic/craete-edit-topic.component';
import { SubjectManagementComponent } from './components/subject-management/subject-management.component';
import { CreateEditSubjectComponent } from './components/subject-management/create-edit-subject/create-edit-subject.component';
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
import { ClassTimeComponent } from './components/class-time-management/class-time.component';
import { CreateEditClassTimeComponent } from './components/class-time-management/create-edit-class-time/create-edit-class-time.component';
import { AboutSubjectComponent } from './components/main-menu/board-moderator/about-subject/about-subject.component';
import { TeachingMaterialComponent } from './components/teaching-material/teaching-material.component';
import { CreateEditTeachingMaterialComponent } from './components/teaching-material/create-edit-teaching-material/create-edit-teaching-material.component';
import { RateStudentComponent } from './components/main-menu/board-moderator/about-subject/rate-student/rate-student.component';
import { SubjectDetailsComponent } from './components/main-menu/board-user/subject-details/subject-details.component';
import { DetailedComponent } from './components/home/details/detailed/detailed.component';
import { ForumManagementComponent } from './components/forum/forum-management/forum-management.component';
import { PodforumComponent } from './components/forum/podforum/podforum.component';
import { ForumComponent } from './components/forum/forum/forum.component';
import { TemaComponent } from './components/forum/tema/tema.component';
import { CreateEditTemaComponent } from './components/forum/tema/create-edit-tema/create-edit-tema.component';
import { CreateEditForumComponent } from './components/forum/forum-management/create-edit-forum/create-edit-forum.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'detailed', component: DetailedComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'user/picksubject', component: PickSubjectComponent },
  { path: 'user/subjectdetails', component: SubjectDetailsComponent },
  { path: 'professor', component: BoardModeratorComponent },
  { path: 'professor/aboutsubject', component: AboutSubjectComponent },
  { path: 'administrator', component: BoardAdminComponent },
  { path: 'userstable', component: UsersManagementComponent },
  { path: 'userstable/createedituser', component: CreateEditUserComponent },
  { path: 'typerankstable', component: TypeRanksManagementComponent },
  { path: 'typerankstable/createeditrank', component: CreateEditTypeRanksComponent },
  { path: 'scientificareatable', component: ScientificAreaManagementComponent },
  { path: 'scientificareatable/createeditscientificarea', component: CreateEditScientificAreaComponent },
  { path: 'rankstable', component: RankManagementComponent },
  { path: 'rankstable/createeditrank', component: CreateEditRankComponent },
  { path: 'yeartable', component: YearManagementComponent },
  { path: 'yeartable/createedit', component: CreateEditYearComponent },
  { path: 'studyprogrammetable', component: StudyProgrammeManagementComponent },
  { path: 'studyprogrammetable/createeditstudyprogramme', component: CreateEditStudyProgrammeComponent },
  { path: 'studentontheyeartable', component:  StudentOnTheYearManagementComponent},
  { path: 'studentontheyeartable/createedit', component: CreateEditStudentOnTheYearComponent },
  { path: 'facultytable', component: FacultyManagementComponent },
  { path: 'facultytable/createeditfaculty', component: CreateEditFacultyComponent },
  { path: 'topictable', component: TopicManagementComponent},
  { path: 'topictable/createedittopic', component: CraeteEditTopicComponent },
  { path: 'subjecttable', component: SubjectManagementComponent},
  { path: 'subjecttable/createeditsubject', component: CreateEditSubjectComponent },
  { path: 'studentservicetable', component: StudentServiceManagementComponent },
  { path: 'studentservicetable/createeditstudentservice', component: CreateEditStudentServiceComponent },
  { path: 'teacheronrealizationtable', component: TeacherOnRealizationManagementComponent},
  { path: 'teacheronrealizationtable/createeditteacheronrealization', component: CreateEditTeacherOnRealizationComponent },
  { path: 'subjectrealizationtable', component: SubjectRealizationManagementComponent},
  { path: 'subjectrealizationtable/createeditsubjectrealization', component: CreateEditSubjectRealizationComponent },
  { path: 'universitytable', component: UniversityManagementComponent},
  { path: 'universitytable/createedituniversity', component: CreateEditUniversityComponent },
  { path: 'evaluationknowledgetable', component: EvaluationKnowledgeManagementComponent},
  { path: 'evaluationknowledgetable/createeditevaluationknowledge', component: CreateEditEvaluationKnowledgeComponent },
  { path: 'filetable', component: FileManagementComponent},
  { path: 'filetable/createeditfile', component: CreateEditFileComponent },
  { path: 'evaluationinstrumenttable', component: EvaluationInstrumentComponent},
  { path: 'evaluationinstrumenttable/createeditevaluationinstrument', component: CreateEditEvaluationInstrumentComponent },
  { path: 'educationgoaltable', component: EducationGoalManagementComponent},
  { path: 'educationgoaltable/createediteducationgoal', component: CreateEditEducationGoalComponent },
  { path: 'classtimetable', component: ClassTimeComponent},
  { path: 'classtimetable/createeditclasstime', component: CreateEditClassTimeComponent },
  { path: 'teachingmaterialtable', component: TeachingMaterialComponent},
  { path: 'teachingmaterialtable/craeteeditteachingmaterial', component: CreateEditTeachingMaterialComponent },
  { path: 'ratestudent', component: RateStudentComponent },
  { path: 'forum', component: ForumComponent },
  { path: 'forum/podforum', component: PodforumComponent },
  { path: 'forum/podforum/tema', component: TemaComponent },
  { path: 'forum/podforum/tema/createedittema', component: CreateEditTemaComponent },
  { path: 'forumtable', component: ForumManagementComponent },
  { path: 'forumtable/createeditforum', component: CreateEditForumComponent },

  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
