import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Novedad } from '../model/novedad';

@Injectable({
    providedIn: 'root'
})

export class NovedadService {

    readonly listarNovedadesURL: string = "/barberia/listar-novedades";
    readonly crearNovedadURL: string = "/barberia/crear-novedad";
    readonly listarFestivosURL: string = "/barberia/listar-festivos/";

    constructor(private http: HttpClient) {
    }

    listarNovedades() {
        return this.http.get<Novedad[]>(this.listarNovedadesURL);
    }

    crearNovedad(novedad: Novedad) {
        let headers = new HttpHeaders();
        headers = headers.append('Content-Type', 'application/json');
        return this.http.post<Novedad>(this.crearNovedadURL, JSON.stringify(novedad), { headers: headers });
    }

    listarFestivos(fechaMinima: string) {
        return this.http.get<Novedad[]>(this.listarFestivosURL + fechaMinima);
    }
}