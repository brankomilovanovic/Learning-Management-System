import { Tema } from "./tema";
import { Objava } from "./objava";
import { User } from "../user";
import { Podforum } from "./podforum";

export interface KorisnikNaForumu {
    id: number,
    vremePrijavljivanja: Date,
    objave: number,
    user: User,
    tema: Set<Tema>,
    objava: Set<Objava>,
    follow_teme: Tema[],
    follow_podforum: Podforum[];
}