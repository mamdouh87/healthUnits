import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DoctorPassImgsComponent } from './list/doctor-pass-imgs.component';
import { DoctorPassImgsDetailComponent } from './detail/doctor-pass-imgs-detail.component';
import { DoctorPassImgsUpdateComponent } from './update/doctor-pass-imgs-update.component';
import { DoctorPassImgsDeleteDialogComponent } from './delete/doctor-pass-imgs-delete-dialog.component';
import { DoctorPassImgsRoutingModule } from './route/doctor-pass-imgs-routing.module';

@NgModule({
  imports: [SharedModule, DoctorPassImgsRoutingModule],
  declarations: [
    DoctorPassImgsComponent,
    DoctorPassImgsDetailComponent,
    DoctorPassImgsUpdateComponent,
    DoctorPassImgsDeleteDialogComponent,
  ],
})
export class DoctorPassImgsModule {}
