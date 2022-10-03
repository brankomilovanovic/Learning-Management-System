import { File } from "./file";

export interface TeachingMaterial {
    id: number,
    naziv: String,
    godinaIzdavanja: Date,
    autori: String[],
    file: File;
}