import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUnits, NewUnits } from '../units.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUnits for edit and NewUnitsFormGroupInput for create.
 */
type UnitsFormGroupInput = IUnits | PartialWithRequiredKeyOf<NewUnits>;

type UnitsFormDefaults = Pick<NewUnits, 'id'>;

type UnitsFormGroupContent = {
  id: FormControl<IUnits['id'] | NewUnits['id']>;
  name: FormControl<IUnits['name']>;
  priority: FormControl<IUnits['priority']>;
  doctorsUnit: FormControl<IUnits['doctorsUnit']>;
  extraPassUnit: FormControl<IUnits['extraPassUnit']>;
};

export type UnitsFormGroup = FormGroup<UnitsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UnitsFormService {
  createUnitsFormGroup(units: UnitsFormGroupInput = { id: null }): UnitsFormGroup {
    const unitsRawValue = {
      ...this.getFormDefaults(),
      ...units,
    };
    return new FormGroup<UnitsFormGroupContent>({
      id: new FormControl(
        { value: unitsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(unitsRawValue.name),
      priority: new FormControl(unitsRawValue.priority),
      doctorsUnit: new FormControl(unitsRawValue.doctorsUnit),
      extraPassUnit: new FormControl(unitsRawValue.extraPassUnit),
    });
  }

  getUnits(form: UnitsFormGroup): IUnits | NewUnits {
    return form.getRawValue() as IUnits | NewUnits;
  }

  resetForm(form: UnitsFormGroup, units: UnitsFormGroupInput): void {
    const unitsRawValue = { ...this.getFormDefaults(), ...units };
    form.reset(
      {
        ...unitsRawValue,
        id: { value: unitsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UnitsFormDefaults {
    return {
      id: null,
    };
  }
}
