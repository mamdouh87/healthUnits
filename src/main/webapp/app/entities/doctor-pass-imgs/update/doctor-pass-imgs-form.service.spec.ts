import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../doctor-pass-imgs.test-samples';

import { DoctorPassImgsFormService } from './doctor-pass-imgs-form.service';

describe('DoctorPassImgs Form Service', () => {
  let service: DoctorPassImgsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoctorPassImgsFormService);
  });

  describe('Service methods', () => {
    describe('createDoctorPassImgsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDoctorPassImgsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dayValue: expect.any(Object),
            img: expect.any(Object),
            unit: expect.any(Object),
            doctor: expect.any(Object),
          })
        );
      });

      it('passing IDoctorPassImgs should create a new form with FormGroup', () => {
        const formGroup = service.createDoctorPassImgsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dayValue: expect.any(Object),
            img: expect.any(Object),
            unit: expect.any(Object),
            doctor: expect.any(Object),
          })
        );
      });
    });

    describe('getDoctorPassImgs', () => {
      it('should return NewDoctorPassImgs for default DoctorPassImgs initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDoctorPassImgsFormGroup(sampleWithNewData);

        const doctorPassImgs = service.getDoctorPassImgs(formGroup) as any;

        expect(doctorPassImgs).toMatchObject(sampleWithNewData);
      });

      it('should return NewDoctorPassImgs for empty DoctorPassImgs initial value', () => {
        const formGroup = service.createDoctorPassImgsFormGroup();

        const doctorPassImgs = service.getDoctorPassImgs(formGroup) as any;

        expect(doctorPassImgs).toMatchObject({});
      });

      it('should return IDoctorPassImgs', () => {
        const formGroup = service.createDoctorPassImgsFormGroup(sampleWithRequiredData);

        const doctorPassImgs = service.getDoctorPassImgs(formGroup) as any;

        expect(doctorPassImgs).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDoctorPassImgs should not enable id FormControl', () => {
        const formGroup = service.createDoctorPassImgsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDoctorPassImgs should disable id FormControl', () => {
        const formGroup = service.createDoctorPassImgsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
