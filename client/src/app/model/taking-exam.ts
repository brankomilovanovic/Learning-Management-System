import { EvaluationKnowledge } from "./evaluation-knowledge";
import { StudentOnTheYear } from "./student-on-the-year";

export interface TakingExam {
    id: number,
    bodovi: number,
    napomena: String,
    studentOnTheYear: StudentOnTheYear,
    evaluationKnowledge: EvaluationKnowledge;
}