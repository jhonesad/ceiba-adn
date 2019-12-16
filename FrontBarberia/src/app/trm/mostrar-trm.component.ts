import { Component, OnInit, Input } from '@angular/core';
import { TrmService } from '../core/services/trm.service';

@Component({
    selector: 'app-mostrar-trm',
    templateUrl: './mostrar-trm.component.html',
    styleUrls: ['./mostrar-trm.component.css']
})

export class MostrarTrmComponent implements OnInit {

    @Input() trm: string;
    
    constructor(
        private trmService: TrmService
    ) { }

    ngOnInit() { 
        this.trmService.obtener().subscribe(
            response => {
                this.trm = response.toString();
            }, (error) => {
                this.trm = "Error consultando la TRM!!";
            }
        );
    }
}