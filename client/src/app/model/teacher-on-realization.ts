import { Professor } from "./professor";
import { SubjectRealization } from "./subject-realization";
import { TypeOfTeaching } from "./type-of-teaching";

export interface TeacherOnRealization {
    id: number,
    brojCasova: number,
    professor: Professor,
    typeOfTeaching: TypeOfTeaching[],
    subjectRealization: SubjectRealization[];
}
