import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExtraPassUnitComponent } from '../list/extra-pass-unit.component';
import { ExtraPassUnitDetailComponent } from '../detail/extra-pass-unit-detail.component';
import { ExtraPassUnitUpdateComponent } from '../update/extra-pass-unit-update.component';
import { ExtraPassUnitRoutingResolveService } from './extra-pass-unit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const extraPassUnitRoute: Routes = [
  {
    path: '',
    component: ExtraPassUnitComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExtraPassUnitDetailComponent,
    resolve: {
      extraPassUnit: ExtraPassUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExtraPassUnitUpdateComponent,
    resolve: {
      extraPassUnit: ExtraPassUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExtraPassUnitUpdateComponent,
    resolve: {
      extraPassUnit: ExtraPassUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(extraPassUnitRoute)],
  exports: [RouterModule],
})
export class ExtraPassUnitRoutingModule {}
