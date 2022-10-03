import { KorisnikNaForumu } from "./korisnik-na-forumu";
import { Tema } from "./tema";
import { File } from "../file";

export interface Objava {
    id: number,
    vremePostavljanja: Date,
    sadrzaj: String,
    tema: Tema,
    prilozi: File[],
    autor: KorisnikNaForumu;
}
