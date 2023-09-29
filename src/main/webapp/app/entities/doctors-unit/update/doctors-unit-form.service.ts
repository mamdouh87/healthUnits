import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDoctorsUnit, NewDoctorsUnit } from '../doctors-unit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDoctorsUnit for edit and NewDoctorsUnitFormGroupInput for create.
 */
type DoctorsUnitFormGroupInput = IDoctorsUnit | PartialWithRequiredKeyOf<NewDoctorsUnit>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDoctorsUnit | NewDoctorsUnit> = Omit<T, 'passDate'> & {
  passDate?: string | null;
};

type DoctorsUnitFormRawValue = FormValueOf<IDoctorsUnit>;

type NewDoctorsUnitFormRawValue = FormValueOf<NewDoctorsUnit>;

type DoctorsUnitFormDefaults = Pick<NewDoctorsUnit, 'id' | 'passDate'>;

type DoctorsUnitFormGroupContent = {
  id: FormControl<DoctorsUnitFormRawValue['id'] | NewDoctorsUnit['id']>;
  dayValue: FormControl<DoctorsUnitFormRawValue['dayValue']>;
  shiftType: FormControl<DoctorsUnitFormRawValue['shiftType']>;
  activeWeek: FormControl<DoctorsUnitFormRawValue['activeWeek']>;
  donePass: FormControl<DoctorsUnitFormRawValue['donePass']>;
  passDate: FormControl<DoctorsUnitFormRawValue['passDate']>;
  unitPassed: FormControl<DoctorsUnitFormRawValue['unitPassed']>;
  passBlocked: FormControl<DoctorsUnitFormRawValue['passBlocked']>;
  justView: FormControl<DoctorsUnitFormRawValue['justView']>;
};

export type DoctorsUnitFormGroup = FormGroup<DoctorsUnitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DoctorsUnitFormService {
  createDoctorsUnitFormGroup(doctorsUnit: DoctorsUnitFormGroupInput = { id: null }): DoctorsUnitFormGroup {
    const doctorsUnitRawValue = this.convertDoctorsUnitToDoctorsUnitRawValue({
      ...this.getFormDefaults(),
      ...doctorsUnit,
    });
    return new FormGroup<DoctorsUnitFormGroupContent>({
      id: new FormControl(
        { value: doctorsUnitRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dayValue: new FormControl(doctorsUnitRawValue.dayValue),
      shiftType: new FormControl(doctorsUnitRawValue.shiftType),
      activeWeek: new FormControl(doctorsUnitRawValue.activeWeek),
      donePass: new FormControl(doctorsUnitRawValue.donePass),
      passDate: new FormControl(doctorsUnitRawValue.passDate),
      unitPassed: new FormControl(doctorsUnitRawValue.unitPassed),
      passBlocked: new FormControl(doctorsUnitRawValue.passBlocked),
      justView: new FormControl(doctorsUnitRawValue.justView),
    });
  }

  getDoctorsUnit(form: DoctorsUnitFormGroup): IDoctorsUnit | NewDoctorsUnit {
    return this.convertDoctorsUnitRawValueToDoctorsUnit(form.getRawValue() as DoctorsUnitFormRawValue | NewDoctorsUnitFormRawValue);
  }

  resetForm(form: DoctorsUnitFormGroup, doctorsUnit: DoctorsUnitFormGroupInput): void {
    const doctorsUnitRawValue = this.convertDoctorsUnitToDoctorsUnitRawValue({ ...this.getFormDefaults(), ...doctorsUnit });
    form.reset(
      {
        ...doctorsUnitRawValue,
        id: { value: doctorsUnitRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DoctorsUnitFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      passDate: currentTime,
    };
  }

  private convertDoctorsUnitRawValueToDoctorsUnit(
    rawDoctorsUnit: DoctorsUnitFormRawValue | NewDoctorsUnitFormRawValue
  ): IDoctorsUnit | NewDoctorsUnit {
    return {
      ...rawDoctorsUnit,
      passDate: dayjs(rawDoctorsUnit.passDate, DATE_TIME_FORMAT),
    };
  }

  private convertDoctorsUnitToDoctorsUnitRawValue(
    doctorsUnit: IDoctorsUnit | (Partial<NewDoctorsUnit> & DoctorsUnitFormDefaults)
  ): DoctorsUnitFormRawValue | PartialWithRequiredKeyOf<NewDoctorsUnitFormRawValue> {
    return {
      ...doctorsUnit,
      passDate: doctorsUnit.passDate ? doctorsUnit.passDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
