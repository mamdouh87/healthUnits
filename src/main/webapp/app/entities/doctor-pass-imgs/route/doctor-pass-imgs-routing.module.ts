import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DoctorPassImgsComponent } from '../list/doctor-pass-imgs.component';
import { DoctorPassImgsDetailComponent } from '../detail/doctor-pass-imgs-detail.component';
import { DoctorPassImgsUpdateComponent } from '../update/doctor-pass-imgs-update.component';
import { DoctorPassImgsRoutingResolveService } from './doctor-pass-imgs-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const doctorPassImgsRoute: Routes = [
  {
    path: '',
    component: DoctorPassImgsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DoctorPassImgsDetailComponent,
    resolve: {
      doctorPassImgs: DoctorPassImgsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DoctorPassImgsUpdateComponent,
    resolve: {
      doctorPassImgs: DoctorPassImgsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DoctorPassImgsUpdateComponent,
    resolve: {
      doctorPassImgs: DoctorPassImgsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(doctorPassImgsRoute)],
  exports: [RouterModule],
})
export class DoctorPassImgsRoutingModule {}
