import { Podforum } from "./podforum";

export interface Forum {
    id: number,
    javni: boolean,
    podforum: Set<Podforum>;
}
