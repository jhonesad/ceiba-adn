import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Barbero } from '../model/barbero';

@Injectable({
    providedIn: 'root'
})
export class BarberoService {

    readonly listarBarberosURL: string = "/api/barbero/listar";
    readonly crearBarberoURL: string = "/api/barbero/crear";

    constructor(private http: HttpClient) {
    }

    listar() {
        return this.http.get<Barbero[]>(this.listarBarberosURL);
    }

    crear(barbero: Barbero) {
        let headers = new HttpHeaders();
        headers = headers.append('Content-Type', 'application/json');
        return this.http.post<Barbero>(this.crearBarberoURL, JSON.stringify(barbero), { headers: headers })
    }
}