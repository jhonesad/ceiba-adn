import { TestBed } from "@angular/core/testing";
import { TrmService } from "./trm.service";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";

describe("TrmService", () => {

    let dataService: TrmService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [TrmService]
        })

        dataService = TestBed.get(TrmService);
        httpMock = TestBed.get(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be initialized', () => {
        expect(dataService).toBeTruthy();
    });

    it("should be able to retrieve the current TRM from the API by GET", () => {
        const mockTrm: String =  "3100.00";

        dataService.obtener().subscribe(response => {
            expect(response).not.toBe(null);
            expect(response).toEqual(mockTrm);
        });

        const request = httpMock.expectOne(dataService.obtenerTrmURL);
        expect(request.request.method).toBe('GET');
        request.flush(mockTrm);
    });
});