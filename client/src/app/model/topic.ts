import { TeachingMaterial } from "./teaching-material";
import { TypeOfTopic } from "./type-of-topic";

export interface Topic {
    id: number,
    opis: string,
    topicType: TypeOfTopic,
    teachingMaterial: TeachingMaterial;
}
