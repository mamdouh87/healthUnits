import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../unit-imgs.test-samples';

import { UnitImgsFormService } from './unit-imgs-form.service';

describe('UnitImgs Form Service', () => {
  let service: UnitImgsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UnitImgsFormService);
  });

  describe('Service methods', () => {
    describe('createUnitImgsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUnitImgsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileDescription: expect.any(Object),
            img: expect.any(Object),
            unit: expect.any(Object),
          })
        );
      });

      it('passing IUnitImgs should create a new form with FormGroup', () => {
        const formGroup = service.createUnitImgsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileDescription: expect.any(Object),
            img: expect.any(Object),
            unit: expect.any(Object),
          })
        );
      });
    });

    describe('getUnitImgs', () => {
      it('should return NewUnitImgs for default UnitImgs initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUnitImgsFormGroup(sampleWithNewData);

        const unitImgs = service.getUnitImgs(formGroup) as any;

        expect(unitImgs).toMatchObject(sampleWithNewData);
      });

      it('should return NewUnitImgs for empty UnitImgs initial value', () => {
        const formGroup = service.createUnitImgsFormGroup();

        const unitImgs = service.getUnitImgs(formGroup) as any;

        expect(unitImgs).toMatchObject({});
      });

      it('should return IUnitImgs', () => {
        const formGroup = service.createUnitImgsFormGroup(sampleWithRequiredData);

        const unitImgs = service.getUnitImgs(formGroup) as any;

        expect(unitImgs).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUnitImgs should not enable id FormControl', () => {
        const formGroup = service.createUnitImgsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUnitImgs should disable id FormControl', () => {
        const formGroup = service.createUnitImgsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
