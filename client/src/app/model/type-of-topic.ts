export interface TypeOfTopic {
    id: number,
    naziv: TypeTopic;
}

export enum TypeTopic {
    PREDMET,
    EVALUACIJA_ZNANJA,
    TERMIN_NASTAVE
}