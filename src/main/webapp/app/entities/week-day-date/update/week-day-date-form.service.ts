import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IWeekDayDate, NewWeekDayDate } from '../week-day-date.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWeekDayDate for edit and NewWeekDayDateFormGroupInput for create.
 */
type WeekDayDateFormGroupInput = IWeekDayDate | PartialWithRequiredKeyOf<NewWeekDayDate>;

type WeekDayDateFormDefaults = Pick<NewWeekDayDate, 'id'>;

type WeekDayDateFormGroupContent = {
  id: FormControl<IWeekDayDate['id'] | NewWeekDayDate['id']>;
  dayValue: FormControl<IWeekDayDate['dayValue']>;
  dayName: FormControl<IWeekDayDate['dayName']>;
  dayDate: FormControl<IWeekDayDate['dayDate']>;
};

export type WeekDayDateFormGroup = FormGroup<WeekDayDateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WeekDayDateFormService {
  createWeekDayDateFormGroup(weekDayDate: WeekDayDateFormGroupInput = { id: null }): WeekDayDateFormGroup {
    const weekDayDateRawValue = {
      ...this.getFormDefaults(),
      ...weekDayDate,
    };
    return new FormGroup<WeekDayDateFormGroupContent>({
      id: new FormControl(
        { value: weekDayDateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dayValue: new FormControl(weekDayDateRawValue.dayValue),
      dayName: new FormControl(weekDayDateRawValue.dayName),
      dayDate: new FormControl(weekDayDateRawValue.dayDate),
    });
  }

  getWeekDayDate(form: WeekDayDateFormGroup): IWeekDayDate | NewWeekDayDate {
    return form.getRawValue() as IWeekDayDate | NewWeekDayDate;
  }

  resetForm(form: WeekDayDateFormGroup, weekDayDate: WeekDayDateFormGroupInput): void {
    const weekDayDateRawValue = { ...this.getFormDefaults(), ...weekDayDate };
    form.reset(
      {
        ...weekDayDateRawValue,
        id: { value: weekDayDateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): WeekDayDateFormDefaults {
    return {
      id: null,
    };
  }
}
