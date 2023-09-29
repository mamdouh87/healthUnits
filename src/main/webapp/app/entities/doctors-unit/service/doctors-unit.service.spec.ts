import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDoctorsUnit } from '../doctors-unit.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../doctors-unit.test-samples';

import { DoctorsUnitService, RestDoctorsUnit } from './doctors-unit.service';

const requireRestSample: RestDoctorsUnit = {
  ...sampleWithRequiredData,
  passDate: sampleWithRequiredData.passDate?.toJSON(),
};

describe('DoctorsUnit Service', () => {
  let service: DoctorsUnitService;
  let httpMock: HttpTestingController;
  let expectedResult: IDoctorsUnit | IDoctorsUnit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DoctorsUnitService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DoctorsUnit', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const doctorsUnit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(doctorsUnit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DoctorsUnit', () => {
      const doctorsUnit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(doctorsUnit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DoctorsUnit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DoctorsUnit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DoctorsUnit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDoctorsUnitToCollectionIfMissing', () => {
      it('should add a DoctorsUnit to an empty array', () => {
        const doctorsUnit: IDoctorsUnit = sampleWithRequiredData;
        expectedResult = service.addDoctorsUnitToCollectionIfMissing([], doctorsUnit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(doctorsUnit);
      });

      it('should not add a DoctorsUnit to an array that contains it', () => {
        const doctorsUnit: IDoctorsUnit = sampleWithRequiredData;
        const doctorsUnitCollection: IDoctorsUnit[] = [
          {
            ...doctorsUnit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDoctorsUnitToCollectionIfMissing(doctorsUnitCollection, doctorsUnit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DoctorsUnit to an array that doesn't contain it", () => {
        const doctorsUnit: IDoctorsUnit = sampleWithRequiredData;
        const doctorsUnitCollection: IDoctorsUnit[] = [sampleWithPartialData];
        expectedResult = service.addDoctorsUnitToCollectionIfMissing(doctorsUnitCollection, doctorsUnit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(doctorsUnit);
      });

      it('should add only unique DoctorsUnit to an array', () => {
        const doctorsUnitArray: IDoctorsUnit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const doctorsUnitCollection: IDoctorsUnit[] = [sampleWithRequiredData];
        expectedResult = service.addDoctorsUnitToCollectionIfMissing(doctorsUnitCollection, ...doctorsUnitArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const doctorsUnit: IDoctorsUnit = sampleWithRequiredData;
        const doctorsUnit2: IDoctorsUnit = sampleWithPartialData;
        expectedResult = service.addDoctorsUnitToCollectionIfMissing([], doctorsUnit, doctorsUnit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(doctorsUnit);
        expect(expectedResult).toContain(doctorsUnit2);
      });

      it('should accept null and undefined values', () => {
        const doctorsUnit: IDoctorsUnit = sampleWithRequiredData;
        expectedResult = service.addDoctorsUnitToCollectionIfMissing([], null, doctorsUnit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(doctorsUnit);
      });

      it('should return initial array if no DoctorsUnit is added', () => {
        const doctorsUnitCollection: IDoctorsUnit[] = [sampleWithRequiredData];
        expectedResult = service.addDoctorsUnitToCollectionIfMissing(doctorsUnitCollection, undefined, null);
        expect(expectedResult).toEqual(doctorsUnitCollection);
      });
    });

    describe('compareDoctorsUnit', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDoctorsUnit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDoctorsUnit(entity1, entity2);
        const compareResult2 = service.compareDoctorsUnit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDoctorsUnit(entity1, entity2);
        const compareResult2 = service.compareDoctorsUnit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDoctorsUnit(entity1, entity2);
        const compareResult2 = service.compareDoctorsUnit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
