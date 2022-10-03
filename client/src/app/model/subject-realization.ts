import { Subject } from "./subject";
import { TeacherOnRealization } from "./teacher-on-realization";

export interface SubjectRealization {
    id: number,
    subject: Subject,
    teacherOnRealization: TeacherOnRealization;
}
