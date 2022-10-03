import { Objava } from "./forum/objava";

export interface File {
    id: number,
    opis: String, 
    url: String,
    objava: Objava;
}
