import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUnitImgs } from '../unit-imgs.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../unit-imgs.test-samples';

import { UnitImgsService } from './unit-imgs.service';

const requireRestSample: IUnitImgs = {
  ...sampleWithRequiredData,
};

describe('UnitImgs Service', () => {
  let service: UnitImgsService;
  let httpMock: HttpTestingController;
  let expectedResult: IUnitImgs | IUnitImgs[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UnitImgsService);
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

    it('should create a UnitImgs', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const unitImgs = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(unitImgs).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UnitImgs', () => {
      const unitImgs = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(unitImgs).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UnitImgs', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UnitImgs', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UnitImgs', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUnitImgsToCollectionIfMissing', () => {
      it('should add a UnitImgs to an empty array', () => {
        const unitImgs: IUnitImgs = sampleWithRequiredData;
        expectedResult = service.addUnitImgsToCollectionIfMissing([], unitImgs);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(unitImgs);
      });

      it('should not add a UnitImgs to an array that contains it', () => {
        const unitImgs: IUnitImgs = sampleWithRequiredData;
        const unitImgsCollection: IUnitImgs[] = [
          {
            ...unitImgs,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUnitImgsToCollectionIfMissing(unitImgsCollection, unitImgs);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UnitImgs to an array that doesn't contain it", () => {
        const unitImgs: IUnitImgs = sampleWithRequiredData;
        const unitImgsCollection: IUnitImgs[] = [sampleWithPartialData];
        expectedResult = service.addUnitImgsToCollectionIfMissing(unitImgsCollection, unitImgs);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(unitImgs);
      });

      it('should add only unique UnitImgs to an array', () => {
        const unitImgsArray: IUnitImgs[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const unitImgsCollection: IUnitImgs[] = [sampleWithRequiredData];
        expectedResult = service.addUnitImgsToCollectionIfMissing(unitImgsCollection, ...unitImgsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const unitImgs: IUnitImgs = sampleWithRequiredData;
        const unitImgs2: IUnitImgs = sampleWithPartialData;
        expectedResult = service.addUnitImgsToCollectionIfMissing([], unitImgs, unitImgs2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(unitImgs);
        expect(expectedResult).toContain(unitImgs2);
      });

      it('should accept null and undefined values', () => {
        const unitImgs: IUnitImgs = sampleWithRequiredData;
        expectedResult = service.addUnitImgsToCollectionIfMissing([], null, unitImgs, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(unitImgs);
      });

      it('should return initial array if no UnitImgs is added', () => {
        const unitImgsCollection: IUnitImgs[] = [sampleWithRequiredData];
        expectedResult = service.addUnitImgsToCollectionIfMissing(unitImgsCollection, undefined, null);
        expect(expectedResult).toEqual(unitImgsCollection);
      });
    });

    describe('compareUnitImgs', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUnitImgs(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUnitImgs(entity1, entity2);
        const compareResult2 = service.compareUnitImgs(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUnitImgs(entity1, entity2);
        const compareResult2 = service.compareUnitImgs(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUnitImgs(entity1, entity2);
        const compareResult2 = service.compareUnitImgs(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
