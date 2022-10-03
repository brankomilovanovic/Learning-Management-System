import { Professor } from "./professor";
import { Year } from "./year";
import { Faculty } from "./faculty";

export interface StudyProgramme {

    id: number,
    description: string,
    name: string,
    director: Professor,
    faculty: Faculty,
    yearOfStudy: Set<Year>


}
