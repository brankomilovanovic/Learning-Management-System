import { Forum } from "./forum";
import { Objava } from "./objava";
import { Tema } from "./tema";

export interface Podforum {
    id: number,
    naziv: String,
    forum: Forum,
    tema: Tema[],
    totalTema: number,
    totalObjava: number,
    lastPost: Objava;
}
