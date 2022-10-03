import { Adress } from "./adress";
import { Professor } from "./professor";
import { StudyProgramme } from "./study-programme";
import { University } from "./university";

export interface Faculty {
    id: number,
    address: Adress,
    contactDetails: string,
    description: string,
    name: string,
    dean: Professor,
    studyProgrammes: Set<StudyProgramme>,
    university: University;
}

