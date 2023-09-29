import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { WeekDayDateFormService, WeekDayDateFormGroup } from './week-day-date-form.service';
import { IWeekDayDate } from '../week-day-date.model';
import { WeekDayDateService } from '../service/week-day-date.service';

@Component({
  selector: 'jhi-week-day-date-update',
  templateUrl: './week-day-date-update.component.html',
})
export class WeekDayDateUpdateComponent implements OnInit {
  isSaving = false;
  weekDayDate: IWeekDayDate | null = null;

  editForm: WeekDayDateFormGroup = this.weekDayDateFormService.createWeekDayDateFormGroup();

  constructor(
    protected weekDayDateService: WeekDayDateService,
    protected weekDayDateFormService: WeekDayDateFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weekDayDate }) => {
      this.weekDayDate = weekDayDate;
      if (weekDayDate) {
        this.updateForm(weekDayDate);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const weekDayDate = this.weekDayDateFormService.getWeekDayDate(this.editForm);
    if (weekDayDate.id !== null) {
      this.subscribeToSaveResponse(this.weekDayDateService.update(weekDayDate));
    } else {
      this.subscribeToSaveResponse(this.weekDayDateService.create(weekDayDate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWeekDayDate>>): void {
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

  protected updateForm(weekDayDate: IWeekDayDate): void {
    this.weekDayDate = weekDayDate;
    this.weekDayDateFormService.resetForm(this.editForm, weekDayDate);
  }
}
