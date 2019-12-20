import { Component, OnInit, Input } from '@angular/core';
import { Barbero } from 'src/app/core/model/barbero';
import { BarberoService } from "../../core/services/barbero.service";
import { Router } from '@angular/router';
import { ValidacionesService } from '../../shared/validaciones.service';

@Component({
    selector: 'app-crear-barbero',
    templateUrl: './crear-barbero.component.html',
    styleUrls: ['./crear-barbero.component.css']
})

export class CrearBarberoComponent implements OnInit {
    
    @Input() barbero: Barbero = new Barbero(null, "");
    errorMessage: string = "";

    constructor(
        private barberoService : BarberoService, 
        private router: Router,
        private validador: ValidacionesService
    ) { }

    ngOnInit() {
        this.errorMessage = "";
    }

    crearBarbero() {
        this.errorMessage = "";

        if(this.validador.isNullEmpty(this.barbero.nombre)) {
            this.errorMessage = "El nombre del barbero es obligatorio";
        } else {
            this.barberoService.crear(this.barbero).subscribe(
                response => {
                    if(response) {
                        this.cargarListaBarberos();
                    } 
                }, (error) => {
                    this.errorMessage = error.error.message;
                }
            );
        }
    }

    cargarListaBarberos() {
        this.router.navigate(['/barberos']);
    }
}