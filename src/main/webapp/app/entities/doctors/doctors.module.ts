import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DoctorsComponent } from './list/doctors.component';
import { DoctorsDetailComponent } from './detail/doctors-detail.component';
import { DoctorsUpdateComponent } from './update/doctors-update.component';
import { DoctorsDeleteDialogComponent } from './delete/doctors-delete-dialog.component';
import { DoctorsRoutingModule } from './route/doctors-routing.module';

@NgModule({
  imports: [SharedModule, DoctorsRoutingModule],
  declarations: [DoctorsComponent, DoctorsDetailComponent, DoctorsUpdateComponent, DoctorsDeleteDialogComponent],
})
export class DoctorsModule {}
