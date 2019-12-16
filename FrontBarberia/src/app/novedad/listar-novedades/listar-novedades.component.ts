import { Component, OnInit, Input } from '@angular/core';
import { Novedad } from 'src/app/core/model/novedad';
import { NovedadService } from "../../core/services/novedad.service";
import { Barbero } from 'src/app/core/model/barbero';
import { Router } from '@angular/router';

@Component({
    selector: 'app-listar-novedades',
    templateUrl: './listar-novedades.component.html',
    styleUrls: ['./listar-novedades.component.css']
})

export class ListarNovedadesComponent implements OnInit {

    @Input() novedades: Novedad[];

    constructor(private novedadService: NovedadService, private router: Router) { }

    ngOnInit() { 
        this.listarNovedades();
    }

    listarNovedades() {
        this.novedadService.listarNovedades().subscribe(
            response => {
                if(response) {
                    response.forEach(nov => {
                        if(nov.barbero == null) {
                            nov.barbero = new Barbero(null, "");
                        }
                    })
                    this.novedades = response;
                } else {
                    console.log("Error:")
                    console.log(response);
                }
            }
        );
    }

    cargarCrearNovedad() {
        this.router.navigate(['/crear-novedad']);
    }
}