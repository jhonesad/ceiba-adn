import { Component, OnInit, Input } from '@angular/core';
import { Barbero } from '../../core/model/barbero';
import { BarberoService } from "../../core/services/barbero.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-listar-barberos',
  templateUrl: './listar-barberos.component.html',
  styleUrls: ['./listar-barberos.component.css']
})
export class ListarBarberosComponent implements OnInit {

  @Input() barberos: Barbero[] = [];

  constructor(private barberoService : BarberoService, private router: Router) { }

  ngOnInit() {
    this.listarBarberos();
  }

  listarBarberos() {
    this.barberoService.listarBarberos().subscribe(
      response => {
        let result = response;
        if(result) {
          this.barberos = result;
        } else {
          console.log('error');
        }
      }
    );
  }

  cargarCrearBarbero() {
    this.router.navigate(['/crear-barbero']);
  }

  getCodigoBarbero(id: number) {
    return ("0000000"+id).substr(-5);
  }
}
