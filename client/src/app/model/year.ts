
import { Student } from "./student";
import { StudentOnTheYear } from "./student-on-the-year";
import { StudyProgramme } from "./study-programme";
import { Subject } from "./subject";

export interface Year {
    id:number,
    year:Date,
    active:boolean,
    studyProgramme: StudyProgramme,
    subjects:Set<Subject>,
    //studentOnTheYear:Set<StudentOnTheYear>

}
