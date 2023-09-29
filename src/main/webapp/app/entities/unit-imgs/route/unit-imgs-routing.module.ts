import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UnitImgsComponent } from '../list/unit-imgs.component';
import { UnitImgsDetailComponent } from '../detail/unit-imgs-detail.component';
import { UnitImgsUpdateComponent } from '../update/unit-imgs-update.component';
import { UnitImgsRoutingResolveService } from './unit-imgs-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const unitImgsRoute: Routes = [
  {
    path: '',
    component: UnitImgsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UnitImgsDetailComponent,
    resolve: {
      unitImgs: UnitImgsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UnitImgsUpdateComponent,
    resolve: {
      unitImgs: UnitImgsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UnitImgsUpdateComponent,
    resolve: {
      unitImgs: UnitImgsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(unitImgsRoute)],
  exports: [RouterModule],
})
export class UnitImgsRoutingModule {}
