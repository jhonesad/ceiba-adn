import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListarBarberosComponent } from './barbero/listar-barberos/listar-barberos.component';
import { CrearBarberoComponent } from './barbero/crear-barbero/crear-barbero.component';
import { ListarNovedadesComponent } from './novedad/listar-novedades/listar-novedades.component';
import { CrearNovedadComponent } from './novedad/crear-novedad/crear-novedad.component';
import { ListarCitasComponent } from "./cita/listar-citas/listar-citas.component";
import { AgendarCitaComponent } from "./cita/agendar-cita/agendar-cita.component";
import { MostrarTrmComponent } from "./trm/mostrar-trm.component";
import { NotFoundComponent } from "./not-found/not-found.component";

const routes: Routes = [
  { path: "barberos", component: ListarBarberosComponent  },
  { path: "crear-barbero", component: CrearBarberoComponent  },
  { path: "novedades", component: ListarNovedadesComponent  },
  { path: "crear-novedad", component: CrearNovedadComponent  },
  { path: "citas", component: ListarCitasComponent },
  { path: "agendar-cita", component: AgendarCitaComponent },
  { path: "trm", component: MostrarTrmComponent },
  { path: "", redirectTo: '/agendar-cita', pathMatch: 'full' },
  { path: "**", component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
