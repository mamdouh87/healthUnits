import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DoctorsComponent } from '../list/doctors.component';
import { DoctorsDetailComponent } from '../detail/doctors-detail.component';
import { DoctorsUpdateComponent } from '../update/doctors-update.component';
import { DoctorsRoutingResolveService } from './doctors-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const doctorsRoute: Routes = [
  {
    path: '',
    component: DoctorsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DoctorsDetailComponent,
    resolve: {
      doctors: DoctorsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DoctorsUpdateComponent,
    resolve: {
      doctors: DoctorsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DoctorsUpdateComponent,
    resolve: {
      doctors: DoctorsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(doctorsRoute)],
  exports: [RouterModule],
})
export class DoctorsRoutingModule {}
