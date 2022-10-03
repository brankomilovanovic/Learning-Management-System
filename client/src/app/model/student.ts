import { Adress } from "./adress";
import { SubjectNotifications } from "./subject-notifications";
import { User } from "./user";

export interface Student {
    id: number,
    jmbg: number,
    dateOfBirth: string,
    address: Adress,
    phoneNumber: number,
    user: User,
    subjectNotifications: SubjectNotifications[];
}
