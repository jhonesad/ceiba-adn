import { TestBed } from "@angular/core/testing";
import { TrmService } from "../core/services/trm.service";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { MostrarTrmComponent } from './mostrar-trm.component';
import { of } from 'rxjs';

describe("MostrarTrmComponent", () => {

    let component: MostrarTrmComponent;
    let trmService: TrmService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [MostrarTrmComponent],
            providers: [TrmService]
        });

        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [TrmService]
        })

        component = TestBed.createComponent(MostrarTrmComponent).componentInstance;
        trmService = TestBed.get(TrmService);
        httpMock = TestBed.get(HttpTestingController);
    });

    it('should get the TRM', () => {
        let trmMock = "3500.0";
        spyOn(trmService, 'obtener').and.returnValue(of(trmMock));

        component.ngOnInit();
        expect(trmService.obtener).toHaveBeenCalled();
        expect(component.trm).toBe(trmMock);
      }
    );
});