import { TestBed } from "@angular/core/testing";
import { NovedadService } from "./novedad.service";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { Novedad } from '../model/novedad';
import * as moment from 'moment';

describe("NovedadService", () => {

    let dataService: NovedadService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [NovedadService]
        })

        dataService = TestBed.get(NovedadService);
        httpMock = TestBed.get(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be initialized', () => {
        expect(dataService).toBeTruthy();
    });

    it("should be able to retrieve novedades from the API by GET", () => {
        const mockNovedades: Novedad[] = [
            {   id: 1, 
                fechaInicio: new Date(), 
                fechaFin: new Date(), 
                barbero: { id: 1, nombre: "test1" },  
                festivo: false,
                descripcion: "test1"
            }
        ];

        dataService.listarNovedades().subscribe(response => {
            expect(response.length).toBe(mockNovedades.length);
            expect(response).toEqual(mockNovedades);
        });

        const request = httpMock.expectOne(dataService.listarNovedadesURL);
        expect(request.request.method).toBe('GET');
        request.flush(mockNovedades);
    });

    it("should be able to schedule an appointment from the API by POST", () => {
        const mockNovedadCrear: Novedad = {   
            id: null, 
            fechaInicio: new Date(), 
            fechaFin: new Date(), 
            barbero: { id: 1, nombre: "test1" },  
            festivo: false,
            descripcion: "test1"
        };

        const mockNovedadCreada: Novedad = {   
            id: 2, 
            fechaInicio: new Date(), 
            fechaFin: new Date(), 
            barbero: { id: 1, nombre: "test1" },  
            festivo: false,
            descripcion: "test1"
        };

        dataService.crearNovedad(mockNovedadCrear).subscribe(response => {
            expect(response).toEqual(mockNovedadCreada);
            expect(response.id).not.toBeNull();
            expect(response.id).toEqual(mockNovedadCreada.id);
        });

        const request = httpMock.expectOne(dataService.crearNovedadURL);
        expect(request.request.method).toBe('POST');
        expect(request.request.headers).not.toBeNull();
        expect(request.request.headers.get("Content-Type")).toEqual("application/json");
        request.flush(mockNovedadCreada);
    });

    it("should be able to retrieve holidays from the API by GET", () => {

        let holiday = moment(new Date(), "DD-MM-YYYY").toDate();
        let strHoliday = holiday.toDateString();

        const mockHolidays: Novedad[] = [
            {   id: 1, 
                fechaInicio: holiday, 
                fechaFin: holiday, 
                barbero: null,  
                festivo: true,
                descripcion: "test1"
            }
        ];

        dataService.listarFestivos(strHoliday).subscribe(response => {
            expect(response.length).toBe(mockHolidays.length);
            expect(response).toEqual(mockHolidays);
        });

        const request = httpMock.expectOne(dataService.listarFestivosURL + strHoliday);
        expect(request.request.method).toBe('GET');
        request.flush(mockHolidays);
    });
});