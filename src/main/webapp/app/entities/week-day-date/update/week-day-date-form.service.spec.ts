import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../week-day-date.test-samples';

import { WeekDayDateFormService } from './week-day-date-form.service';

describe('WeekDayDate Form Service', () => {
  let service: WeekDayDateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WeekDayDateFormService);
  });

  describe('Service methods', () => {
    describe('createWeekDayDateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWeekDayDateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dayValue: expect.any(Object),
            dayName: expect.any(Object),
            dayDate: expect.any(Object),
          })
        );
      });

      it('passing IWeekDayDate should create a new form with FormGroup', () => {
        const formGroup = service.createWeekDayDateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dayValue: expect.any(Object),
            dayName: expect.any(Object),
            dayDate: expect.any(Object),
          })
        );
      });
    });

    describe('getWeekDayDate', () => {
      it('should return NewWeekDayDate for default WeekDayDate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createWeekDayDateFormGroup(sampleWithNewData);

        const weekDayDate = service.getWeekDayDate(formGroup) as any;

        expect(weekDayDate).toMatchObject(sampleWithNewData);
      });

      it('should return NewWeekDayDate for empty WeekDayDate initial value', () => {
        const formGroup = service.createWeekDayDateFormGroup();

        const weekDayDate = service.getWeekDayDate(formGroup) as any;

        expect(weekDayDate).toMatchObject({});
      });

      it('should return IWeekDayDate', () => {
        const formGroup = service.createWeekDayDateFormGroup(sampleWithRequiredData);

        const weekDayDate = service.getWeekDayDate(formGroup) as any;

        expect(weekDayDate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWeekDayDate should not enable id FormControl', () => {
        const formGroup = service.createWeekDayDateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWeekDayDate should disable id FormControl', () => {
        const formGroup = service.createWeekDayDateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
