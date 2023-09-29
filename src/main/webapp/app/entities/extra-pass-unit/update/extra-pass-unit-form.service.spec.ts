import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../extra-pass-unit.test-samples';

import { ExtraPassUnitFormService } from './extra-pass-unit-form.service';

describe('ExtraPassUnit Form Service', () => {
  let service: ExtraPassUnitFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExtraPassUnitFormService);
  });

  describe('Service methods', () => {
    describe('createExtraPassUnitFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createExtraPassUnitFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dayValue: expect.any(Object),
            shiftType: expect.any(Object),
            activeWeek: expect.any(Object),
            donePass: expect.any(Object),
            passDate: expect.any(Object),
            unitPassed: expect.any(Object),
          })
        );
      });

      it('passing IExtraPassUnit should create a new form with FormGroup', () => {
        const formGroup = service.createExtraPassUnitFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dayValue: expect.any(Object),
            shiftType: expect.any(Object),
            activeWeek: expect.any(Object),
            donePass: expect.any(Object),
            passDate: expect.any(Object),
            unitPassed: expect.any(Object),
          })
        );
      });
    });

    describe('getExtraPassUnit', () => {
      it('should return NewExtraPassUnit for default ExtraPassUnit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createExtraPassUnitFormGroup(sampleWithNewData);

        const extraPassUnit = service.getExtraPassUnit(formGroup) as any;

        expect(extraPassUnit).toMatchObject(sampleWithNewData);
      });

      it('should return NewExtraPassUnit for empty ExtraPassUnit initial value', () => {
        const formGroup = service.createExtraPassUnitFormGroup();

        const extraPassUnit = service.getExtraPassUnit(formGroup) as any;

        expect(extraPassUnit).toMatchObject({});
      });

      it('should return IExtraPassUnit', () => {
        const formGroup = service.createExtraPassUnitFormGroup(sampleWithRequiredData);

        const extraPassUnit = service.getExtraPassUnit(formGroup) as any;

        expect(extraPassUnit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IExtraPassUnit should not enable id FormControl', () => {
        const formGroup = service.createExtraPassUnitFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewExtraPassUnit should disable id FormControl', () => {
        const formGroup = service.createExtraPassUnitFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
