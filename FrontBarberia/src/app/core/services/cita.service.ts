import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Cita } from '../model/cita';

@Injectable({providedIn: 'root'})
export class CitaService {

    readonly listarCitasURL: string = "/barberia/listar-citas";
    readonly agendarCitaURL: string = "/barberia/agendar-cita";

    constructor(private httpClient: HttpClient) { }
    
    listarCitas() {
        return this.httpClient.get<Cita[]>(this.listarCitasURL);
    }

    agendarCita(cita: Cita) {
        let headers = new HttpHeaders();
        headers = headers.append('Content-Type', 'application/json');
        return this.httpClient.post<Cita>(this.agendarCitaURL, JSON.stringify(cita), { headers: headers });
    }
}