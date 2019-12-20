import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Novedad } from '../model/novedad';

@Injectable({
    providedIn: 'root'
})

export class NovedadService {

    readonly listarNovedadesURL: string = "/api/novedad/listar";
    readonly crearNovedadURL: string = "/api/novedad/crear";
    readonly listarFestivosURL: string = "/api/novedad/listar-festivos/";

    constructor(private http: HttpClient) {
    }

    listar() {
        return this.http.get<Novedad[]>(this.listarNovedadesURL);
    }

    crear(novedad: Novedad) {
        let headers = new HttpHeaders();
        headers = headers.append('Content-Type', 'application/json');
        return this.http.post<Novedad>(this.crearNovedadURL, JSON.stringify(novedad), { headers: headers });
    }

    listarFestivos(fechaMinima: string) {
        return this.http.get<Novedad[]>(this.listarFestivosURL + fechaMinima);
    }
}