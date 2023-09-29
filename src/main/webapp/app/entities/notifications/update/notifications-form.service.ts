import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INotifications, NewNotifications } from '../notifications.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotifications for edit and NewNotificationsFormGroupInput for create.
 */
type NotificationsFormGroupInput = INotifications | PartialWithRequiredKeyOf<NewNotifications>;

type NotificationsFormDefaults = Pick<NewNotifications, 'id'>;

type NotificationsFormGroupContent = {
  id: FormControl<INotifications['id'] | NewNotifications['id']>;
  title: FormControl<INotifications['title']>;
  message: FormControl<INotifications['message']>;
  status: FormControl<INotifications['status']>;
  dayValue: FormControl<INotifications['dayValue']>;
  unit: FormControl<INotifications['unit']>;
};

export type NotificationsFormGroup = FormGroup<NotificationsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotificationsFormService {
  createNotificationsFormGroup(notifications: NotificationsFormGroupInput = { id: null }): NotificationsFormGroup {
    const notificationsRawValue = {
      ...this.getFormDefaults(),
      ...notifications,
    };
    return new FormGroup<NotificationsFormGroupContent>({
      id: new FormControl(
        { value: notificationsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(notificationsRawValue.title),
      message: new FormControl(notificationsRawValue.message),
      status: new FormControl(notificationsRawValue.status),
      dayValue: new FormControl(notificationsRawValue.dayValue),
      unit: new FormControl(notificationsRawValue.unit),
    });
  }

  getNotifications(form: NotificationsFormGroup): INotifications | NewNotifications {
    return form.getRawValue() as INotifications | NewNotifications;
  }

  resetForm(form: NotificationsFormGroup, notifications: NotificationsFormGroupInput): void {
    const notificationsRawValue = { ...this.getFormDefaults(), ...notifications };
    form.reset(
      {
        ...notificationsRawValue,
        id: { value: notificationsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NotificationsFormDefaults {
    return {
      id: null,
    };
  }
}
