import { ScientificArea } from "./scientific-area";
import { TypeRanks } from "./type-ranks";
import { Professor } from "./professor";

export interface Rank {
    id: number,
    electionDate: Date,
    terminationDate: Date,
    typeRanks: TypeRanks,
    scientificArea: ScientificArea,
    professor: Professor;
}