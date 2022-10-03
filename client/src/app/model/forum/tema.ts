import { Podforum } from "./podforum";
import { Objava } from "./objava";
import { KorisnikNaForumu } from "./korisnik-na-forumu";

export interface Tema {
    id: number,
    naziv: String,
    pregleda: number,
    podforum: Podforum,
    autor: KorisnikNaForumu,
    objava: Set<Objava>,
    prvaObjava: Objava,
    zadnjaObjava: Objava,
    brojObjava: number;
}
