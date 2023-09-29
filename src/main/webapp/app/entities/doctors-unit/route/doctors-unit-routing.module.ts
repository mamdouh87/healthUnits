import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DoctorsUnitComponent } from '../list/doctors-unit.component';
import { DoctorsUnitDetailComponent } from '../detail/doctors-unit-detail.component';
import { DoctorsUnitUpdateComponent } from '../update/doctors-unit-update.component';
import { DoctorsUnitRoutingResolveService } from './doctors-unit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const doctorsUnitRoute: Routes = [
  {
    path: '',
    component: DoctorsUnitComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DoctorsUnitDetailComponent,
    resolve: {
      doctorsUnit: DoctorsUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DoctorsUnitUpdateComponent,
    resolve: {
      doctorsUnit: DoctorsUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DoctorsUnitUpdateComponent,
    resolve: {
      doctorsUnit: DoctorsUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(doctorsUnitRoute)],
  exports: [RouterModule],
})
export class DoctorsUnitRoutingModule {}
