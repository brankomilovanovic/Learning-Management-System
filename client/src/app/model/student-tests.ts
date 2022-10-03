import { StudentOnTheYear } from "./student-on-the-year";
import { Subject } from "./subject";

export interface StudentTests {
    id: number,
    kolokvijum1: number,
    kolokvijum2: number,
    ispit: number,
    aktivnost: number;
    ocena: number,
    studentOnTheYear: StudentOnTheYear,
    subject: Subject;
}
