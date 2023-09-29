import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../units.test-samples';

import { UnitsFormService } from './units-form.service';

describe('Units Form Service', () => {
  let service: UnitsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UnitsFormService);
  });

  describe('Service methods', () => {
    describe('createUnitsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUnitsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            priority: expect.any(Object),
            doctorsUnit: expect.any(Object),
            extraPassUnit: expect.any(Object),
          })
        );
      });

      it('passing IUnits should create a new form with FormGroup', () => {
        const formGroup = service.createUnitsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            priority: expect.any(Object),
            doctorsUnit: expect.any(Object),
            extraPassUnit: expect.any(Object),
          })
        );
      });
    });

    describe('getUnits', () => {
      it('should return NewUnits for default Units initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUnitsFormGroup(sampleWithNewData);

        const units = service.getUnits(formGroup) as any;

        expect(units).toMatchObject(sampleWithNewData);
      });

      it('should return NewUnits for empty Units initial value', () => {
        const formGroup = service.createUnitsFormGroup();

        const units = service.getUnits(formGroup) as any;

        expect(units).toMatchObject({});
      });

      it('should return IUnits', () => {
        const formGroup = service.createUnitsFormGroup(sampleWithRequiredData);

        const units = service.getUnits(formGroup) as any;

        expect(units).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUnits should not enable id FormControl', () => {
        const formGroup = service.createUnitsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUnits should disable id FormControl', () => {
        const formGroup = service.createUnitsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
