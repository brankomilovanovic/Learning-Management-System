export interface Role {
    id: number,
    name: ERole;
}

export enum ERole{
    ROLE_USER,
    ROLE_STUDENT,
    ROLE_PROFESSOR,
    ROLE_ADMINISTRATOR
}