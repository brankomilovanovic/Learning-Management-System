import { StudentService } from "./student-service";
import { StudentTests } from "./student-tests";
import { User } from "./user";

export interface History {
    id: number,
    user: User;
    tests:StudentTests
    enroll:StudentService

}