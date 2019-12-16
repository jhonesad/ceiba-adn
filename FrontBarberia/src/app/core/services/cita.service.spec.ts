import { TestBed } from "@angular/core/testing";
import { CitaService } from "./cita.service";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { Cita } from '../model/cita';

describe('CitaService' , () => {

    let dataService: CitaService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [CitaService]
        })

        dataService = TestBed.get(CitaService);
        httpMock = TestBed.get(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be initialized', () => {
        expect(dataService).toBeTruthy();
    });

    it("should be able to retrieve citas from the API by GET", () => {
        const mockCitas: Cita[] = [
            {   id: 1, 
                fecha: new Date(), 
                barbero: { id: 1, nombre: "test1" },  
                corteCabello: true, 
                corteBarba: false, 
                lavado: true, 
                nombreCliente: "test1"
            }
        ];

        dataService.listarCitas().subscribe(response => {
            expect(response.length).toBe(mockCitas.length);
            expect(response).toEqual(mockCitas);
        });

        const request = httpMock.expectOne(dataService.listarCitasURL);
        expect(request.request.method).toBe('GET');
        request.flush(mockCitas);
    });

    it("should be able to schedule an appointment from the API by POST", () => {
        const mockCitaCrear: Cita = {   
            id: null, 
            fecha: new Date(), 
            barbero: { id: 1, nombre: "test1" },  
            corteCabello: true, 
            corteBarba: false, 
            lavado: true, 
            nombreCliente: "test1"
        };

        const mockCitaCreada: Cita = {   
            id: 1, 
            fecha: new Date(), 
            barbero: { id: 1, nombre: "test1" },  
            corteCabello: true, 
            corteBarba: false, 
            lavado: true, 
            nombreCliente: "test1"
        };

        dataService.agendarCita(mockCitaCrear).subscribe(response => {
            expect(response).toEqual(mockCitaCreada);
            expect(response.id).not.toBeNull();
        });

        const request = httpMock.expectOne(dataService.agendarCitaURL);
        expect(request.request.method).toBe('POST');
        expect(request.request.headers).not.toBeNull();
        expect(request.request.headers.get("Content-Type")).toEqual("application/json");
        request.flush(mockCitaCreada);
    });
});