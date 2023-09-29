import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../doctors-unit.test-samples';

import { DoctorsUnitFormService } from './doctors-unit-form.service';

describe('DoctorsUnit Form Service', () => {
  let service: DoctorsUnitFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoctorsUnitFormService);
  });

  describe('Service methods', () => {
    describe('createDoctorsUnitFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDoctorsUnitFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dayValue: expect.any(Object),
            shiftType: expect.any(Object),
            activeWeek: expect.any(Object),
            donePass: expect.any(Object),
            passDate: expect.any(Object),
            unitPassed: expect.any(Object),
            passBlocked: expect.any(Object),
            justView: expect.any(Object),
          })
        );
      });

      it('passing IDoctorsUnit should create a new form with FormGroup', () => {
        const formGroup = service.createDoctorsUnitFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dayValue: expect.any(Object),
            shiftType: expect.any(Object),
            activeWeek: expect.any(Object),
            donePass: expect.any(Object),
            passDate: expect.any(Object),
            unitPassed: expect.any(Object),
            passBlocked: expect.any(Object),
            justView: expect.any(Object),
          })
        );
      });
    });

    describe('getDoctorsUnit', () => {
      it('should return NewDoctorsUnit for default DoctorsUnit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDoctorsUnitFormGroup(sampleWithNewData);

        const doctorsUnit = service.getDoctorsUnit(formGroup) as any;

        expect(doctorsUnit).toMatchObject(sampleWithNewData);
      });

      it('should return NewDoctorsUnit for empty DoctorsUnit initial value', () => {
        const formGroup = service.createDoctorsUnitFormGroup();

        const doctorsUnit = service.getDoctorsUnit(formGroup) as any;

        expect(doctorsUnit).toMatchObject({});
      });

      it('should return IDoctorsUnit', () => {
        const formGroup = service.createDoctorsUnitFormGroup(sampleWithRequiredData);

        const doctorsUnit = service.getDoctorsUnit(formGroup) as any;

        expect(doctorsUnit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDoctorsUnit should not enable id FormControl', () => {
        const formGroup = service.createDoctorsUnitFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDoctorsUnit should disable id FormControl', () => {
        const formGroup = service.createDoctorsUnitFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
