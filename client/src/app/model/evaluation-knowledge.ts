import { Topic } from "./topic";
import { EvaluationInstrument } from "./evaluation-instrument";
import { TypeEvaluation } from "./type-evaluation";
import { TakingExam } from "./taking-exam";
import { SubjectRealization } from "./subject-realization";

export interface EvaluationKnowledge {
    id:number,
    vremePocetka: Date,
    vremeKraja: Date,
    bodovi: number,
    topic: Topic,
    evaluationInstrument: EvaluationInstrument,
    typeEvaluation: TypeEvaluation,
    subjectRealization: SubjectRealization,
    takingExam: Set<TakingExam>,
}
