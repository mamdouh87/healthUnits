import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NotificationsFormService, NotificationsFormGroup } from './notifications-form.service';
import { INotifications } from '../notifications.model';
import { NotificationsService } from '../service/notifications.service';
import { IUnits } from 'app/entities/units/units.model';
import { UnitsService } from 'app/entities/units/service/units.service';

@Component({
  selector: 'jhi-notifications-update',
  templateUrl: './notifications-update.component.html',
})
export class NotificationsUpdateComponent implements OnInit {
  isSaving = false;
  notifications: INotifications | null = null;

  unitsSharedCollection: IUnits[] = [];

  editForm: NotificationsFormGroup = this.notificationsFormService.createNotificationsFormGroup();

  constructor(
    protected notificationsService: NotificationsService,
    protected notificationsFormService: NotificationsFormService,
    protected unitsService: UnitsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUnits = (o1: IUnits | null, o2: IUnits | null): boolean => this.unitsService.compareUnits(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notifications }) => {
      this.notifications = notifications;
      if (notifications) {
        this.updateForm(notifications);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notifications = this.notificationsFormService.getNotifications(this.editForm);
    if (notifications.id !== null) {
      this.subscribeToSaveResponse(this.notificationsService.update(notifications));
    } else {
      this.subscribeToSaveResponse(this.notificationsService.create(notifications));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotifications>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(notifications: INotifications): void {
    this.notifications = notifications;
    this.notificationsFormService.resetForm(this.editForm, notifications);

    this.unitsSharedCollection = this.unitsService.addUnitsToCollectionIfMissing<IUnits>(this.unitsSharedCollection, notifications.unit);
  }

  protected loadRelationshipsOptions(): void {
    this.unitsService
      .query()
      .pipe(map((res: HttpResponse<IUnits[]>) => res.body ?? []))
      .pipe(map((units: IUnits[]) => this.unitsService.addUnitsToCollectionIfMissing<IUnits>(units, this.notifications?.unit)))
      .subscribe((units: IUnits[]) => (this.unitsSharedCollection = units));
  }
}
