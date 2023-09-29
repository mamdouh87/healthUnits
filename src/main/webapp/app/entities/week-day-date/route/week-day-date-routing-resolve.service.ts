import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWeekDayDate } from '../week-day-date.model';
import { WeekDayDateService } from '../service/week-day-date.service';

@Injectable({ providedIn: 'root' })
export class WeekDayDateRoutingResolveService implements Resolve<IWeekDayDate | null> {
  constructor(protected service: WeekDayDateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWeekDayDate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((weekDayDate: HttpResponse<IWeekDayDate>) => {
          if (weekDayDate.body) {
            return of(weekDayDate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
