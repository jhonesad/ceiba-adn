import { Component, OnInit, Input } from '@angular/core';
import { NovedadService } from "../../core/services/novedad.service";
import { BarberoService } from "../../core/services/barbero.service";
import { Router } from "@angular/router";
import { Novedad } from 'src/app/core/model/novedad';
import { Barbero } from 'src/app/core/model/barbero';

import * as moment from 'moment';
import { ValidacionesService } from '../../shared/validaciones.service';

@Component({
    selector: 'app-crear-novedad',
    templateUrl: './crear-novedad.component.html',
    styleUrls: ['./crear-novedad.component.css']
})
export class CrearNovedadComponent implements OnInit {

    barberos: Barbero[];
    @Input() novedad: Novedad;
    @Input() fechaInicio: any;
    @Input() fechaFin: any;
    @Input() fechaFestivo: any;

    errorMessage: string = "";
    today = moment();
    dateFormatDateTime: string = "DD-MM-YYYY hh:mm A";
    datePickerConfigDateTime = {
        format: this.dateFormatDateTime,
        drops: 'down',
        opens: 'right',
        closeOnSelect: true,
        min: this.today
    };

    dateFormatDate: string = "DD-MM-YYYY";
    datePickerConfigDate = {
        format: this.dateFormatDate,
        drops: 'down',
        opens: 'right',
        closeOnSelect: true,
        min: this.today
    };

    constructor(
        private novedadService: NovedadService, 
        private barberoService: BarberoService, 
        private router: Router,
        private validador: ValidacionesService
    ) { }

    ngOnInit() {
        this.errorMessage = "";
        this.barberos = [new Barbero(0, "Seleccionar")];
        this.novedad = new Novedad(null, null, null, this.barberos[0], "", false);

        this.barberoService.listar().subscribe(
            response => {
                if(response) {
                    response.forEach(r => {
                        this.barberos[this.barberos.length] = r;
                    });
                }
            }, (error) => {
                console.log(error);
            }
        );
    }

    cargarListaNovedades() {
        this.router.navigate(['/novedades']);
    }

    crearNovedad() {
        if(this.validarCrearNovedad()) {
            this.asignarFechaInicioFinNovedad();
            if(this.novedad.festivo == true) {
                this.novedad.barbero = this.barberos[0];
            }
            
            this.novedadService.crear(this.novedad).subscribe(
                response => {
                    if(response) {
                        this.cargarListaNovedades();
                    } 
                }, (error) => {
                    this.errorMessage = error.error.message;
                }
            );
        }
    }

    asignarFechaInicioFinNovedad() {
        let fechaI:Date = null;
        let fechaF:Date = null;

        if(this.novedad.festivo == true) {
            fechaI = this.setDateTime(this.fechaFestivo, this.dateFormatDate, 0, 0, 0, 0); 
            fechaF = this.setDateTime(this.fechaFestivo, this.dateFormatDate, 23, 59, 59, 999);
        } else {
            fechaI = this.setDateTime(this.fechaInicio, this.dateFormatDateTime, null, null, 0, 0); 
            fechaF = this.setDateTime(this.fechaFin, this.dateFormatDateTime, null, null, 0, 0);
        }

        this.novedad.fechaInicio = fechaI;
        this.novedad.fechaFin = fechaF;
    }

    setDateTime(strFecha: string, format: string, horas: number, minutos: number, segundos: number, mill: number): Date {
        let fecha = moment(strFecha, format).toDate();
        if(horas != null) fecha.setHours(horas);
        if(minutos != null) fecha.setMinutes(minutos);
        if(segundos != null) fecha.setSeconds(segundos);
        if(mill != null) fecha.setMilliseconds(mill);
        return fecha;
    }

    validarCrearNovedad() {
        this.errorMessage = "";
        let validacion = true;

        if(this.novedad.festivo == true) {
            if(validacion && this.validador.isNullUndefined(this.fechaFestivo)) {
                validacion = false;
                this.errorMessage = "Debe seleccionar una fecha";
            }

            if(validacion && this.validador.isNullUndefined(this.fechaFestivo)) {
                validacion = false;
                this.errorMessage = "Debe seleccionar una fecha";
            }
        } else {
            if(validacion && (this.validador.isNullUndefined(this.novedad.barbero) || this.validador.isNullCero(this.novedad.barbero.id))) {
                validacion = false;
                this.errorMessage = "Debe seleccionar un barbero";
            }

            if(validacion && this.validador.isNullUndefined(this.fechaInicio)) {
                validacion = false;
                this.errorMessage = "Debe seleccionar una fecha y hora de inicio";
            }

            if(validacion && this.validador.isNullUndefined(this.fechaFin)) {
                validacion = false;
                this.errorMessage = "Debe seleccionar una fecha y hora de fin";
            }
        }

        if(validacion && this.validador.isNullEmpty(this.novedad.descripcion)) {
            validacion = false;
            this.errorMessage = "La descripción es obligatoria";
        }

        return validacion;
    }
}