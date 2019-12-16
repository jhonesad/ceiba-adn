import { Barbero } from './barbero';

export class Novedad {

    constructor(
        public id: number,
        public fechaInicio: Date,
        public fechaFin: Date,
        public barbero: Barbero,
        public descripcion: string,
        public festivo: boolean
    ) { 
        if(barbero == null) {
            this.barbero = new Barbero(null, "");
        }
    }
}