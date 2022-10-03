import { Professor } from "./professor";
import { Role } from "./role";
import { Student } from "./student";

export interface User {
    id: number,
    name: string,
    surname: string,
    username: string,
    email: string,
    password: string,
    student:Student,
    professor:Professor,
    roles: Set<Role>;
}
