import { Adress } from "./adress";
import { User } from "./user";

export interface Professor {
    id: number,
    jmbg: number,
    dateOfBirth: string,
    address: Adress,
    phoneNumber: number,
    biography: string,
    user: User;
}

