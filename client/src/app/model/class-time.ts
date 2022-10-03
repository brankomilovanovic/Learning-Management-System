import { SubjectRealization } from "./subject-realization";
import { Topic } from "./topic";
import { TypeOfTeaching } from "./type-of-teaching";

export interface ClassTime {
    id: number,
    vremePocetka: Date,
    vremeKraja: Date,
    topic: Topic,
    typeOfTeaching: TypeOfTeaching,
    subjectRealization: SubjectRealization;

}
