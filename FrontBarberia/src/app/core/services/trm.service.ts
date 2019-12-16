import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class TrmService {

    readonly obtenerTrmURL: string = "/prod/Dmservices/Utilities.svc/GetTRM";

    constructor(
        private httpClient: HttpClient
    ) { }
    
    obtener() {
        return this.httpClient.get(this.obtenerTrmURL);
    }
}