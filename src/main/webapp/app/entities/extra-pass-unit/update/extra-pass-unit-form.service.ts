import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IExtraPassUnit, NewExtraPassUnit } from '../extra-pass-unit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExtraPassUnit for edit and NewExtraPassUnitFormGroupInput for create.
 */
type ExtraPassUnitFormGroupInput = IExtraPassUnit | PartialWithRequiredKeyOf<NewExtraPassUnit>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IExtraPassUnit | NewExtraPassUnit> = Omit<T, 'passDate'> & {
  passDate?: string | null;
};

type ExtraPassUnitFormRawValue = FormValueOf<IExtraPassUnit>;

type NewExtraPassUnitFormRawValue = FormValueOf<NewExtraPassUnit>;

type ExtraPassUnitFormDefaults = Pick<NewExtraPassUnit, 'id' | 'passDate'>;

type ExtraPassUnitFormGroupContent = {
  id: FormControl<ExtraPassUnitFormRawValue['id'] | NewExtraPassUnit['id']>;
  dayValue: FormControl<ExtraPassUnitFormRawValue['dayValue']>;
  shiftType: FormControl<ExtraPassUnitFormRawValue['shiftType']>;
  activeWeek: FormControl<ExtraPassUnitFormRawValue['activeWeek']>;
  donePass: FormControl<ExtraPassUnitFormRawValue['donePass']>;
  passDate: FormControl<ExtraPassUnitFormRawValue['passDate']>;
  unitPassed: FormControl<ExtraPassUnitFormRawValue['unitPassed']>;
};

export type ExtraPassUnitFormGroup = FormGroup<ExtraPassUnitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExtraPassUnitFormService {
  createExtraPassUnitFormGroup(extraPassUnit: ExtraPassUnitFormGroupInput = { id: null }): ExtraPassUnitFormGroup {
    const extraPassUnitRawValue = this.convertExtraPassUnitToExtraPassUnitRawValue({
      ...this.getFormDefaults(),
      ...extraPassUnit,
    });
    return new FormGroup<ExtraPassUnitFormGroupContent>({
      id: new FormControl(
        { value: extraPassUnitRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dayValue: new FormControl(extraPassUnitRawValue.dayValue),
      shiftType: new FormControl(extraPassUnitRawValue.shiftType),
      activeWeek: new FormControl(extraPassUnitRawValue.activeWeek),
      donePass: new FormControl(extraPassUnitRawValue.donePass),
      passDate: new FormControl(extraPassUnitRawValue.passDate),
      unitPassed: new FormControl(extraPassUnitRawValue.unitPassed),
    });
  }

  getExtraPassUnit(form: ExtraPassUnitFormGroup): IExtraPassUnit | NewExtraPassUnit {
    return this.convertExtraPassUnitRawValueToExtraPassUnit(form.getRawValue() as ExtraPassUnitFormRawValue | NewExtraPassUnitFormRawValue);
  }

  resetForm(form: ExtraPassUnitFormGroup, extraPassUnit: ExtraPassUnitFormGroupInput): void {
    const extraPassUnitRawValue = this.convertExtraPassUnitToExtraPassUnitRawValue({ ...this.getFormDefaults(), ...extraPassUnit });
    form.reset(
      {
        ...extraPassUnitRawValue,
        id: { value: extraPassUnitRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ExtraPassUnitFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      passDate: currentTime,
    };
  }

  private convertExtraPassUnitRawValueToExtraPassUnit(
    rawExtraPassUnit: ExtraPassUnitFormRawValue | NewExtraPassUnitFormRawValue
  ): IExtraPassUnit | NewExtraPassUnit {
    return {
      ...rawExtraPassUnit,
      passDate: dayjs(rawExtraPassUnit.passDate, DATE_TIME_FORMAT),
    };
  }

  private convertExtraPassUnitToExtraPassUnitRawValue(
    extraPassUnit: IExtraPassUnit | (Partial<NewExtraPassUnit> & ExtraPassUnitFormDefaults)
  ): ExtraPassUnitFormRawValue | PartialWithRequiredKeyOf<NewExtraPassUnitFormRawValue> {
    return {
      ...extraPassUnit,
      passDate: extraPassUnit.passDate ? extraPassUnit.passDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
