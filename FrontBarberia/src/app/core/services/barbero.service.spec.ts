import { TestBed } from "@angular/core/testing";
import { BarberoService } from "./barbero.service";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { Barbero } from '../model/barbero';

describe('BarberoService', () => {

    let dataService: BarberoService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [BarberoService]
        })

        // inject the service
        dataService = TestBed.get(BarberoService);
        httpMock = TestBed.get(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be initialized', () => {
        expect(dataService).toBeTruthy();
    });

    it("should be able to retrieve barberos from the API by GET", () => {
        const mockBarberos: Barbero[] = [
            { id: 1, nombre: "test 1" }, { id: 2, nombre: "test 2" }
        ];

        dataService.listarBarberos().subscribe(response => {
            expect(response.length).toBe(2);
            expect(response).toEqual(mockBarberos);
        });

        const request = httpMock.expectOne(dataService.listarBarberosURL);
        expect(request.request.method).toBe('GET');
        request.flush(mockBarberos);
    });

    it("should be able to create a Barbero from the API by POST", () => {
        const mockBarberoCrear: Barbero = { id: null, nombre: "test 1" };
        const mockBarberoCreado: Barbero = { id: 1, nombre: "test 1" };

        dataService.crearBarbero(mockBarberoCrear).subscribe(response => {
            expect(response).toEqual(mockBarberoCreado);
            expect(response.id).not.toBeNull();
        });

        const request = httpMock.expectOne(dataService.crearBarberoURL);
        expect(request.request.method).toBe('POST');
        expect(request.request.headers).not.toBeNull();
        expect(request.request.headers.get("Content-Type")).toEqual("application/json");
        request.flush(mockBarberoCreado);
    });
});