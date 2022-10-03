import { AfterViewInit, Component, OnInit } from '@angular/core';
import { University } from 'src/app/model/university';
import { UniversityService } from 'src/app/service/university/university.service';
import { UserService } from 'src/app/service/user/user.service';


import {
  trigger,
  state,
  style,
  animate,
  transition,
  keyframes,
  query,
  stagger
} from '@angular/animations'
import { Faculty } from 'src/app/model/faculty';
import { FacultyService } from 'src/app/service/faculty/faculty.service';
import { StudyProgramme } from 'src/app/model/study-programme';
import { StudyProgrammeService } from 'src/app/service/study-programme/study-programme.service';
import { Router } from '@angular/router';
import { Subject } from 'src/app/model/subject';
import { SubjectService } from 'src/app/service/subject/subject.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  object: any;

  kliknutUni :boolean=false;
  kliknutFaks :boolean=false;
  kliknutSub :boolean=false;
  kliknutSp :boolean=false;
  // uniOne :boolean=false;
  // fOne :boolean=false;

  obSelected: boolean=false;

  univers : University;
  fac : Faculty;
  sub : Subject;
  spp : StudyProgramme;


  content: string;
  unis : University[] = [];
  university : University []=[];
  faculty: Faculty[]=[];
  sp : StudyProgramme[]=[];
  sb : Subject[]=[];


  s : University []=[]

  constructor(private userService: UserService, private universityService : UniversityService, private facultyService : FacultyService, 
    private spService : StudyProgrammeService, private router: Router, private sbService : SubjectService) { }
 

  ngOnInit(): void {
   
    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );

    this.universityService.getAll().subscribe((university: University[]) => { 
      this.university = university; 
    });
    this.facultyService.getAll().subscribe((faculty: Faculty[]) => { 
      this.faculty = faculty; 
    });
    this.spService.getAll().subscribe((sp: StudyProgramme[]) => { 
      this.sp = sp; 
    });
    this.sbService.getAll().subscribe((sb: Subject[]) => { 
      this.sb = sb; 
    });
  }
  ionViewDidLoad(){
    setTimeout(() => {
      window.scrollTo({ top: 500, behavior: 'smooth' })
    }, 0);
}
  show(nus: any): void{
    
    console.log(typeof(nus));
    if(nus == "uni"){
      this.obSelected=false;
      this.kliknutSp =false;
      this.kliknutSub = false;
      this.kliknutFaks = false;
      this.kliknutUni = true;
    }
    else if(nus == "facs"){
      this.obSelected=false;
      this.kliknutSp =false;
      this.kliknutSub = false;
      this.kliknutUni = false;
      this.kliknutFaks = true
      
    }
    else if(nus == "sub"){
      this.obSelected=false;
      this.kliknutSp =false;
      this.kliknutUni = false;
      this.kliknutFaks = false;
      this.kliknutSub = true;
    }
    else if(nus == "spp"){
      this.obSelected=false;
      this.kliknutUni = false;
      this.kliknutFaks = false;
      this.kliknutSub = false;
      this.kliknutSp =true;
    }
    // else if(Object.keys(nus).includes('faculty')){
    //   this.kliknutUni = false;
    //   this.kliknutFaks = false;
    //   this.uniOne= true;
    //   this.fOne=false;
    //   this.fac = null;
    //   this.univers = nus;
    // }
    // else if(Object.keys(nus).includes('studyProgrammes')){
    //   // nus.
    //   // nus.interface.name
    //   this.kliknutUni = false;
    //   this.kliknutFaks = false;
    //   this.uniOne= false;
    //   this.fOne= true;
    //   this.univers = null;
    //   this.fac = nus;
    // }

    // else if(typeof(nus) === "object"){
    //   this.obSelected=true;
    //   this.object = nus;
    //   this.kliknutUni = false;
    //   this.kliknutFaks = false;
    //   this.kliknutSub = false;

    // }
    // this.ionViewDidLoad();
    // window.scrollTo({ top: 500, behavior: 'smooth' })
    // window.scrollTo(250, 510);
    setTimeout(() => {
      window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' })
    }, 50);

  }


  about(object: any){
    console.log(object);
    this.router.navigate(['/detailed', {objDetails: JSON.stringify(object)}]);
  }


  







}
