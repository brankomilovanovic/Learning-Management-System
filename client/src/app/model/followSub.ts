import { Subject } from "./subject";
import { User } from "./user";

export interface followSub {
    id: number;
    user: User;
    subjects: Set<Subject>;
}