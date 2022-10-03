import { Student } from "./student";
import { StudentTests } from "./student-tests";

export interface StudentOnTheYear {
    id:number,
    dateOfEntry:Date,
    indexNo:string,
    student:Student,
    studentTests: Set<StudentTests>;
    //yearofstudy:Year
}
