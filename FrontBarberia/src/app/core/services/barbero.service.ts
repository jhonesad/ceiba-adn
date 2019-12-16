import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Barbero } from '../model/barbero';

@Injectable({
    providedIn: 'root'
})
export class BarberoService {

    readonly listarBarberosURL: string = "/barberia/listar-barberos";
    readonly crearBarberoURL: string = "/barberia/crear-barbero";

    constructor(private http: HttpClient) {
    }

    listarBarberos() {
        return this.http.get<Barbero[]>(this.listarBarberosURL);
    }

    crearBarbero(barbero: Barbero) {
        let headers = new HttpHeaders();
        headers = headers.append('Content-Type', 'application/json');
        return this.http.post<Barbero>(this.crearBarberoURL, JSON.stringify(barbero), { headers: headers })
    }
}