import { Barbero } from './barbero';

export class Cita {

    constructor(
        public id:number,
        public fecha:Date,
        public barbero:Barbero,
        public corteCabello:boolean,
        public corteBarba:boolean,
        public lavado:boolean,
        public nombreCliente:string
    ) {}
}