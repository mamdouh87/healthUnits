import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDoctors } from '../doctors.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../doctors.test-samples';

import { DoctorsService } from './doctors.service';

const requireRestSample: IDoctors = {
  ...sampleWithRequiredData,
};

describe('Doctors Service', () => {
  let service: DoctorsService;
  let httpMock: HttpTestingController;
  let expectedResult: IDoctors | IDoctors[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DoctorsService);
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

    it('should create a Doctors', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const doctors = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(doctors).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Doctors', () => {
      const doctors = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(doctors).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Doctors', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Doctors', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Doctors', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDoctorsToCollectionIfMissing', () => {
      it('should add a Doctors to an empty array', () => {
        const doctors: IDoctors = sampleWithRequiredData;
        expectedResult = service.addDoctorsToCollectionIfMissing([], doctors);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(doctors);
      });

      it('should not add a Doctors to an array that contains it', () => {
        const doctors: IDoctors = sampleWithRequiredData;
        const doctorsCollection: IDoctors[] = [
          {
            ...doctors,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDoctorsToCollectionIfMissing(doctorsCollection, doctors);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Doctors to an array that doesn't contain it", () => {
        const doctors: IDoctors = sampleWithRequiredData;
        const doctorsCollection: IDoctors[] = [sampleWithPartialData];
        expectedResult = service.addDoctorsToCollectionIfMissing(doctorsCollection, doctors);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(doctors);
      });

      it('should add only unique Doctors to an array', () => {
        const doctorsArray: IDoctors[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const doctorsCollection: IDoctors[] = [sampleWithRequiredData];
        expectedResult = service.addDoctorsToCollectionIfMissing(doctorsCollection, ...doctorsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const doctors: IDoctors = sampleWithRequiredData;
        const doctors2: IDoctors = sampleWithPartialData;
        expectedResult = service.addDoctorsToCollectionIfMissing([], doctors, doctors2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(doctors);
        expect(expectedResult).toContain(doctors2);
      });

      it('should accept null and undefined values', () => {
        const doctors: IDoctors = sampleWithRequiredData;
        expectedResult = service.addDoctorsToCollectionIfMissing([], null, doctors, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(doctors);
      });

      it('should return initial array if no Doctors is added', () => {
        const doctorsCollection: IDoctors[] = [sampleWithRequiredData];
        expectedResult = service.addDoctorsToCollectionIfMissing(doctorsCollection, undefined, null);
        expect(expectedResult).toEqual(doctorsCollection);
      });
    });

    describe('compareDoctors', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDoctors(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDoctors(entity1, entity2);
        const compareResult2 = service.compareDoctors(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDoctors(entity1, entity2);
        const compareResult2 = service.compareDoctors(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDoctors(entity1, entity2);
        const compareResult2 = service.compareDoctors(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
