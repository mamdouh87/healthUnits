import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WeekDayDateComponent } from '../list/week-day-date.component';
import { WeekDayDateDetailComponent } from '../detail/week-day-date-detail.component';
import { WeekDayDateUpdateComponent } from '../update/week-day-date-update.component';
import { WeekDayDateRoutingResolveService } from './week-day-date-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const weekDayDateRoute: Routes = [
  {
    path: '',
    component: WeekDayDateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WeekDayDateDetailComponent,
    resolve: {
      weekDayDate: WeekDayDateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WeekDayDateUpdateComponent,
    resolve: {
      weekDayDate: WeekDayDateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WeekDayDateUpdateComponent,
    resolve: {
      weekDayDate: WeekDayDateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(weekDayDateRoute)],
  exports: [RouterModule],
})
export class WeekDayDateRoutingModule {}
