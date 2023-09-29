import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../doctors.test-samples';

import { DoctorsFormService } from './doctors-form.service';

describe('Doctors Form Service', () => {
  let service: DoctorsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoctorsFormService);
  });

  describe('Service methods', () => {
    describe('createDoctorsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDoctorsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            perferedDay: expect.any(Object),
            doubleShift: expect.any(Object),
            shiftType: expect.any(Object),
            perferedUnit: expect.any(Object),
            doctorsUnit: expect.any(Object),
            extraPassUnit: expect.any(Object),
          })
        );
      });

      it('passing IDoctors should create a new form with FormGroup', () => {
        const formGroup = service.createDoctorsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            perferedDay: expect.any(Object),
            doubleShift: expect.any(Object),
            shiftType: expect.any(Object),
            perferedUnit: expect.any(Object),
            doctorsUnit: expect.any(Object),
            extraPassUnit: expect.any(Object),
          })
        );
      });
    });

    describe('getDoctors', () => {
      it('should return NewDoctors for default Doctors initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDoctorsFormGroup(sampleWithNewData);

        const doctors = service.getDoctors(formGroup) as any;

        expect(doctors).toMatchObject(sampleWithNewData);
      });

      it('should return NewDoctors for empty Doctors initial value', () => {
        const formGroup = service.createDoctorsFormGroup();

        const doctors = service.getDoctors(formGroup) as any;

        expect(doctors).toMatchObject({});
      });

      it('should return IDoctors', () => {
        const formGroup = service.createDoctorsFormGroup(sampleWithRequiredData);

        const doctors = service.getDoctors(formGroup) as any;

        expect(doctors).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDoctors should not enable id FormControl', () => {
        const formGroup = service.createDoctorsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDoctors should disable id FormControl', () => {
        const formGroup = service.createDoctorsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
