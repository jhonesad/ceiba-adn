import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ValidacionesService {
    constructor() { }
    
    isNullUndefined(value: any) {
        return value === null || value === undefined || value == null || value == undefined;
    }    

    isNullCero(value: number) {
        return this.isNullUndefined(value) || value == 0;
    }

    isNullEmpty(value: string) {
        return this.isNullUndefined(value) || value.trim().length == 0;
    }

    isListNullEmpty(value: any[]) {
        return this.isNullUndefined(value) || value.length == 0;
    }
}