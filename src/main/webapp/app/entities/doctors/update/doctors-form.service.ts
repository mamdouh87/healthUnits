import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDoctors, NewDoctors } from '../doctors.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDoctors for edit and NewDoctorsFormGroupInput for create.
 */
type DoctorsFormGroupInput = IDoctors | PartialWithRequiredKeyOf<NewDoctors>;

type DoctorsFormDefaults = Pick<NewDoctors, 'id'>;

type DoctorsFormGroupContent = {
  id: FormControl<IDoctors['id'] | NewDoctors['id']>;
  name: FormControl<IDoctors['name']>;
  perferedDay: FormControl<IDoctors['perferedDay']>;
  doubleShift: FormControl<IDoctors['doubleShift']>;
  shiftType: FormControl<IDoctors['shiftType']>;
  perferedUnit: FormControl<IDoctors['perferedUnit']>;
  doctorsUnit: FormControl<IDoctors['doctorsUnit']>;
  extraPassUnit: FormControl<IDoctors['extraPassUnit']>;
};

export type DoctorsFormGroup = FormGroup<DoctorsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DoctorsFormService {
  createDoctorsFormGroup(doctors: DoctorsFormGroupInput = { id: null }): DoctorsFormGroup {
    const doctorsRawValue = {
      ...this.getFormDefaults(),
      ...doctors,
    };
    return new FormGroup<DoctorsFormGroupContent>({
      id: new FormControl(
        { value: doctorsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(doctorsRawValue.name),
      perferedDay: new FormControl(doctorsRawValue.perferedDay),
      doubleShift: new FormControl(doctorsRawValue.doubleShift),
      shiftType: new FormControl(doctorsRawValue.shiftType),
      perferedUnit: new FormControl(doctorsRawValue.perferedUnit),
      doctorsUnit: new FormControl(doctorsRawValue.doctorsUnit),
      extraPassUnit: new FormControl(doctorsRawValue.extraPassUnit),
    });
  }

  getDoctors(form: DoctorsFormGroup): IDoctors | NewDoctors {
    return form.getRawValue() as IDoctors | NewDoctors;
  }

  resetForm(form: DoctorsFormGroup, doctors: DoctorsFormGroupInput): void {
    const doctorsRawValue = { ...this.getFormDefaults(), ...doctors };
    form.reset(
      {
        ...doctorsRawValue,
        id: { value: doctorsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DoctorsFormDefaults {
    return {
      id: null,
    };
  }
}
