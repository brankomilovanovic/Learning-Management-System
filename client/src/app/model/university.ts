import { Professor } from "./professor";
import { Adress } from "./adress";
import { Faculty } from "./faculty";
export interface University {

    id: number,
    address: Adress,
    contactDetails: string,
    description : string,
    establishmentDate: Date,
    name: string,
    faculty: Set<Faculty>
    headmaster: Professor,

}
