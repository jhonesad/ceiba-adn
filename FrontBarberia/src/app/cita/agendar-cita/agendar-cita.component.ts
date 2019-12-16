import { Component, OnInit, Input } from '@angular/core';
import { CitaService } from "../../core/services/cita.service";
import { BarberoService } from "../../core/services/barbero.service";
import { NovedadService } from "../../core/services/novedad.service";
import { Cita } from "../../core/model/cita";
import { Barbero } from '../../core/model/barbero';
import { Horario } from '../../core/model/horario';
import { Novedad } from 'src/app/core/model/novedad';

import { Router } from "@angular/router";
import * as moment from 'moment';
import { ValidacionesService } from '../../shared/validaciones.service';


@Component({
    selector: 'app-agendar-cita',
    templateUrl: './agendar-cita.component.html',
    styleUrls: ['./agendar-cita.component.css']
})

export class AgendarCitaComponent implements OnInit {
    
    @Input() cita: Cita;
    @Input() fecha: any;
    @Input() hora: Horario;

    barberos: Barbero[];
    horarios: Horario[];
    festivos: Novedad[];
    dateFormat: string = "DD-MM-YYYY";
    errorMessage: string = "";
    today = moment();
    maxHoraAtencion = 20;
    datePickerConfig = {
        format: this.dateFormat,
        drops: 'down',
        opens: 'right',
        min: this.today,
        closeOnSelect: true
    };

    constructor(
        private citaService: CitaService,
        private barberoService: BarberoService, 
        private novedadService: NovedadService,
        private router: Router,
        private validador: ValidacionesService
    ) { }

    ngOnInit() { 
        this.errorMessage = "";
        let dFecha = this.today.toDate();
        if(this.esHoraMayorAMaximaHoraServicio(dFecha)) {
            let hoyCopy = this.today.format("YYYYMMDD");
            let tomorrow = moment(hoyCopy, "YYYYMMDD").add(1, 'day').toDate();
            tomorrow.setHours(0,0,0,0);
            this.datePickerConfig.min = moment(tomorrow);
            this.fecha = moment(tomorrow);
        } else {
            this.fecha = moment(this.today);
        }
        
        this.barberos = [new Barbero(0, "Seleccionar")];
        this.cita = new Cita(null, null, this.barberos[0], false, false, false, "");

        this.listarBarberos();
        this.listarFestivos();
    }

    listarBarberos() {
        this.barberoService.listarBarberos().subscribe(
            response => {
                if(response) {
                    response.forEach(r => {
                        this.barberos[this.barberos.length] = r;
                    });
                } else {
                    console.log("Error");
                    console.log(response);
                }
            }, (error) => {
                console.log(error.error.message);
            }
        );
    }

    listarFestivos() {
        let fechaMinima = moment(this.fecha).format(this.dateFormat);
        this.novedadService.listarFestivos(fechaMinima).subscribe(
            response => {
                this.festivos = response;
            }, (error) => {
                console.log(error.error.message);
            }
        );
    }

    listarDisponibilidadBarberoXFecha() {
        this.horarios = [new Horario("Seleccionar", null)];
        this.hora = this.horarios[0];

        let dFecha = moment(this.fecha, this.dateFormat).toDate();
        for(let x = 8; x <= this.maxHoraAtencion; x++) {
            if(moment(dFecha).format('YYYYMMDD') == this.today.format('YYYYMMDD')) {
                let hora1 = this.getFechaConHora(dFecha, x, 0);
                let hora2 = this.getFechaConHora(dFecha, x, 30);
                if(Number(moment().format("HHmm")) < Number(moment(hora1.fecha).format("HHmm"))) {
                    this.horarios[this.horarios.length] = hora1;
                }
                if(Number(moment().format("HHmm")) < Number(moment(hora2.fecha).format("HHmm"))) {
                    this.horarios[this.horarios.length] = hora2;
                }
            } else {
                this.horarios[this.horarios.length] = this.getFechaConHora(dFecha, x, 0);
                this.horarios[this.horarios.length] = this.getFechaConHora(dFecha, x, 30);
            }
        }
    }

    esHoraMayorAMaximaHoraServicio(date: Date) {
        return !this.validador.isNullUndefined(date) 
            && Number(moment(date).format("HHmm")) >= Number(("00"+this.maxHoraAtencion).substr(-2)+"30");
    }

    getFechaConHora(fecha: Date, hora: number, minutos: number) : Horario {
        let dFecha = new Date(fecha);
        dFecha.setHours(hora, minutos, 0, 0);
        return new Horario(moment(dFecha).format("hh:mm A"), dFecha);
    }

    cargarListaCitas() {
        this.router.navigate(['/citas']);
    }

    mostrarValorServicio() {
        return "$"+this.calcularValorServicio();
    }

    calcularValorServicio() {
        let valorServicio = 0;
        if(this.cita.corteCabello == true) {
            valorServicio = valorServicio + 15000;
        }
        if(this.cita.corteBarba == true) {
            valorServicio = valorServicio + 10000;
        }
        if(this.cita.lavado == true) {
            valorServicio = valorServicio + 5000;
        }
        if(this.esDiaFestivo()) {
            valorServicio = (valorServicio * 0.35) + valorServicio;
        }
        return valorServicio;
    }

    esDiaFestivo() {
        let fecha = moment(this.fecha, this.dateFormat).toDate();
        let esFestivo = false;
        if(!this.validador.isListNullEmpty(this.festivos)) {
            this.festivos.forEach(f => {
                if(moment(fecha).isBetween(moment(f.fechaInicio), moment(f.fechaFin), 'days', '[]')) {
                    esFestivo = true;
                }
            });
        }
        return esFestivo;
    }

    agendarCita(){
        if(this.validarAgendarCita()) {
            this.cita.fecha = this.hora.fecha;
            console.log(this.cita);
            this.citaService.agendarCita(this.cita).subscribe(
                response => {
                    if(response) {
                        this.cargarListaCitas();
                    } 
                }, (error) => {
                    this.errorMessage = error.error.message;
                }
            );
        }
    }

    validarAgendarCita() {
        this.errorMessage = "";
        let validacion = true;

        if(this.validador.isNullUndefined(this.cita.barbero) || this.validador.isNullCero(this.cita.barbero.id)) {
            validacion = false;
            this.errorMessage = "Debe seleccionar un barbero";
        }

        if(validacion && (this.validador.isNullUndefined(this.hora) || this.validador.isNullUndefined(this.hora.fecha))) {
            validacion = false;
            this.errorMessage = "Debe seleccionar una fecha y hora";
        }

        if(validacion && this.cita.corteCabello == false 
            && this.cita.corteBarba == false && this.cita.lavado == false) {
            validacion = false;
            this.errorMessage = "Debe seleccionar al menos un servicio";
        }

        if(validacion && this.validador.isNullEmpty(this.cita.nombreCliente)) {
            validacion = false;
            this.errorMessage = "El nombre del cliente es requerido";
        }

        return validacion;
    }

}