import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Cita } from '../model/cita';

@Injectable({providedIn: 'root'})
export class CitaService {

    readonly listarCitasURL: string = "/api/cita/listar";
    readonly agendarCitaURL: string = "/api/cita/agendar";

    constructor(private httpClient: HttpClient) { }
    
    listar() {
        return this.httpClient.get<Cita[]>(this.listarCitasURL);
    }

    agendar(cita: Cita) {
        let headers = new HttpHeaders();
        headers = headers.append('Content-Type', 'application/json');
        return this.httpClient.post<Cita>(this.agendarCitaURL, JSON.stringify(cita), { headers: headers });
    }
}