import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWeekDayDate } from '../week-day-date.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../week-day-date.test-samples';

import { WeekDayDateService } from './week-day-date.service';

const requireRestSample: IWeekDayDate = {
  ...sampleWithRequiredData,
};

describe('WeekDayDate Service', () => {
  let service: WeekDayDateService;
  let httpMock: HttpTestingController;
  let expectedResult: IWeekDayDate | IWeekDayDate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WeekDayDateService);
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

    it('should create a WeekDayDate', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const weekDayDate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(weekDayDate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WeekDayDate', () => {
      const weekDayDate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(weekDayDate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a WeekDayDate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WeekDayDate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a WeekDayDate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWeekDayDateToCollectionIfMissing', () => {
      it('should add a WeekDayDate to an empty array', () => {
        const weekDayDate: IWeekDayDate = sampleWithRequiredData;
        expectedResult = service.addWeekDayDateToCollectionIfMissing([], weekDayDate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(weekDayDate);
      });

      it('should not add a WeekDayDate to an array that contains it', () => {
        const weekDayDate: IWeekDayDate = sampleWithRequiredData;
        const weekDayDateCollection: IWeekDayDate[] = [
          {
            ...weekDayDate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWeekDayDateToCollectionIfMissing(weekDayDateCollection, weekDayDate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WeekDayDate to an array that doesn't contain it", () => {
        const weekDayDate: IWeekDayDate = sampleWithRequiredData;
        const weekDayDateCollection: IWeekDayDate[] = [sampleWithPartialData];
        expectedResult = service.addWeekDayDateToCollectionIfMissing(weekDayDateCollection, weekDayDate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(weekDayDate);
      });

      it('should add only unique WeekDayDate to an array', () => {
        const weekDayDateArray: IWeekDayDate[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const weekDayDateCollection: IWeekDayDate[] = [sampleWithRequiredData];
        expectedResult = service.addWeekDayDateToCollectionIfMissing(weekDayDateCollection, ...weekDayDateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const weekDayDate: IWeekDayDate = sampleWithRequiredData;
        const weekDayDate2: IWeekDayDate = sampleWithPartialData;
        expectedResult = service.addWeekDayDateToCollectionIfMissing([], weekDayDate, weekDayDate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(weekDayDate);
        expect(expectedResult).toContain(weekDayDate2);
      });

      it('should accept null and undefined values', () => {
        const weekDayDate: IWeekDayDate = sampleWithRequiredData;
        expectedResult = service.addWeekDayDateToCollectionIfMissing([], null, weekDayDate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(weekDayDate);
      });

      it('should return initial array if no WeekDayDate is added', () => {
        const weekDayDateCollection: IWeekDayDate[] = [sampleWithRequiredData];
        expectedResult = service.addWeekDayDateToCollectionIfMissing(weekDayDateCollection, undefined, null);
        expect(expectedResult).toEqual(weekDayDateCollection);
      });
    });

    describe('compareWeekDayDate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWeekDayDate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareWeekDayDate(entity1, entity2);
        const compareResult2 = service.compareWeekDayDate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareWeekDayDate(entity1, entity2);
        const compareResult2 = service.compareWeekDayDate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareWeekDayDate(entity1, entity2);
        const compareResult2 = service.compareWeekDayDate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
