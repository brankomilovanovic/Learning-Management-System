import { StudentOnTheYear } from "./student-on-the-year";
import { StudyProgramme } from "./study-programme";
import { Year } from "./year";

export interface StudentService {
    id: number,
    student:StudentOnTheYear,
    studyProgrammes:StudyProgramme,
    year:Set<Year>,
    choosed:boolean
}
