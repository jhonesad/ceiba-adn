import { Component, OnInit, Input } from '@angular/core';
import { CitaService } from "../../core/services/cita.service";
import { Cita } from "../../core/model/cita";
import { Router } from "@angular/router";

@Component({
    selector: 'app-listar-citas',
    templateUrl: './listar-citas.component.html',
    styleUrls: ['./listar-citas.component.css']
})

export class ListarCitasComponent implements OnInit {
    
    @Input() citas: Cita[];

    constructor(
        private citaService: CitaService,
        private router: Router
    ) { }

    ngOnInit() {
        this.listarCitas();
     }

    listarCitas() {
        this.citaService.listar().subscribe(
            response => {
                if(response) {
                    this.citas = response;
                } else {
                    console.log("Error");
                    console.log(response);
                }
            }
        );
    }

    cargarAgendarCita() {
        this.router.navigate(["/agendar-cita"]);
    }
}