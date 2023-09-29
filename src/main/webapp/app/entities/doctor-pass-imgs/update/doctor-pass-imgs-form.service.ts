import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDoctorPassImgs, NewDoctorPassImgs } from '../doctor-pass-imgs.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDoctorPassImgs for edit and NewDoctorPassImgsFormGroupInput for create.
 */
type DoctorPassImgsFormGroupInput = IDoctorPassImgs | PartialWithRequiredKeyOf<NewDoctorPassImgs>;

type DoctorPassImgsFormDefaults = Pick<NewDoctorPassImgs, 'id'>;

type DoctorPassImgsFormGroupContent = {
  id: FormControl<IDoctorPassImgs['id'] | NewDoctorPassImgs['id']>;
  dayValue: FormControl<IDoctorPassImgs['dayValue']>;
  img: FormControl<IDoctorPassImgs['img']>;
  imgContentType: FormControl<IDoctorPassImgs['imgContentType']>;
  unit: FormControl<IDoctorPassImgs['unit']>;
  doctor: FormControl<IDoctorPassImgs['doctor']>;
};

export type DoctorPassImgsFormGroup = FormGroup<DoctorPassImgsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DoctorPassImgsFormService {
  createDoctorPassImgsFormGroup(doctorPassImgs: DoctorPassImgsFormGroupInput = { id: null }): DoctorPassImgsFormGroup {
    const doctorPassImgsRawValue = {
      ...this.getFormDefaults(),
      ...doctorPassImgs,
    };
    return new FormGroup<DoctorPassImgsFormGroupContent>({
      id: new FormControl(
        { value: doctorPassImgsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dayValue: new FormControl(doctorPassImgsRawValue.dayValue),
      img: new FormControl(doctorPassImgsRawValue.img),
      imgContentType: new FormControl(doctorPassImgsRawValue.imgContentType),
      unit: new FormControl(doctorPassImgsRawValue.unit),
      doctor: new FormControl(doctorPassImgsRawValue.doctor),
    });
  }

  getDoctorPassImgs(form: DoctorPassImgsFormGroup): IDoctorPassImgs | NewDoctorPassImgs {
    return form.getRawValue() as IDoctorPassImgs | NewDoctorPassImgs;
  }

  resetForm(form: DoctorPassImgsFormGroup, doctorPassImgs: DoctorPassImgsFormGroupInput): void {
    const doctorPassImgsRawValue = { ...this.getFormDefaults(), ...doctorPassImgs };
    form.reset(
      {
        ...doctorPassImgsRawValue,
        id: { value: doctorPassImgsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DoctorPassImgsFormDefaults {
    return {
      id: null,
    };
  }
}
