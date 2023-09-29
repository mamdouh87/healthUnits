import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUnitImgs, NewUnitImgs } from '../unit-imgs.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUnitImgs for edit and NewUnitImgsFormGroupInput for create.
 */
type UnitImgsFormGroupInput = IUnitImgs | PartialWithRequiredKeyOf<NewUnitImgs>;

type UnitImgsFormDefaults = Pick<NewUnitImgs, 'id'>;

type UnitImgsFormGroupContent = {
  id: FormControl<IUnitImgs['id'] | NewUnitImgs['id']>;
  fileDescription: FormControl<IUnitImgs['fileDescription']>;
  img: FormControl<IUnitImgs['img']>;
  imgContentType: FormControl<IUnitImgs['imgContentType']>;
  unit: FormControl<IUnitImgs['unit']>;
};

export type UnitImgsFormGroup = FormGroup<UnitImgsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UnitImgsFormService {
  createUnitImgsFormGroup(unitImgs: UnitImgsFormGroupInput = { id: null }): UnitImgsFormGroup {
    const unitImgsRawValue = {
      ...this.getFormDefaults(),
      ...unitImgs,
    };
    return new FormGroup<UnitImgsFormGroupContent>({
      id: new FormControl(
        { value: unitImgsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fileDescription: new FormControl(unitImgsRawValue.fileDescription),
      img: new FormControl(unitImgsRawValue.img),
      imgContentType: new FormControl(unitImgsRawValue.imgContentType),
      unit: new FormControl(unitImgsRawValue.unit),
    });
  }

  getUnitImgs(form: UnitImgsFormGroup): IUnitImgs | NewUnitImgs {
    return form.getRawValue() as IUnitImgs | NewUnitImgs;
  }

  resetForm(form: UnitImgsFormGroup, unitImgs: UnitImgsFormGroupInput): void {
    const unitImgsRawValue = { ...this.getFormDefaults(), ...unitImgs };
    form.reset(
      {
        ...unitImgsRawValue,
        id: { value: unitImgsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UnitImgsFormDefaults {
    return {
      id: null,
    };
  }
}
