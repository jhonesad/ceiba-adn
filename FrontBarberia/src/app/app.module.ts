import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DpDatePickerModule } from 'ng2-date-picker';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CrearBarberoComponent } from './barbero/crear-barbero/crear-barbero.component';
import { ListarBarberosComponent } from './barbero/listar-barberos/listar-barberos.component';
import { CrearNovedadComponent } from './novedad/crear-novedad/crear-novedad.component';
import { ListarNovedadesComponent } from './novedad/listar-novedades/listar-novedades.component';
import { AgendarCitaComponent } from "./cita/agendar-cita/agendar-cita.component";
import { ListarCitasComponent } from "./cita/listar-citas/listar-citas.component";
import { MostrarTrmComponent } from "./trm/mostrar-trm.component";
import { NotFoundComponent } from "./not-found/not-found.component";
import { from } from 'rxjs';

@NgModule({
  declarations: [
    AppComponent,
    CrearBarberoComponent,
    ListarBarberosComponent,
    CrearNovedadComponent,
    ListarNovedadesComponent,
    AgendarCitaComponent,
    ListarCitasComponent,
    MostrarTrmComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    DpDatePickerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
