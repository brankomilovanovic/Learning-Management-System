import { SubjectNotifications } from "./subject-notifications";
import { Topic } from "./topic";

export interface Subject {
    id: number,
    naziv: string,
    espb: number,
    obavezan: boolean,
    brojPredavanja: number,
    brojVezbi: number,
    drugiObliciNastave: number,
    istrazivackiRad: number,
    ostaliCasovi: number,
    topic: Set<Topic>,
    silabus: string,
    subjectNotifications: SubjectNotifications[];

}
