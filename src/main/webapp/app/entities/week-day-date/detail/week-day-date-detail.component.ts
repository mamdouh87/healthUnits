import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWeekDayDate } from '../week-day-date.model';

@Component({
  selector: 'jhi-week-day-date-detail',
  templateUrl: './week-day-date-detail.component.html',
})
export class WeekDayDateDetailComponent implements OnInit {
  weekDayDate: IWeekDayDate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weekDayDate }) => {
      this.weekDayDate = weekDayDate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
