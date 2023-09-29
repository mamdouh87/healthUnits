import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DoctorsUnitComponent } from './list/doctors-unit.component';
import { DoctorsUnitDetailComponent } from './detail/doctors-unit-detail.component';
import { DoctorsUnitUpdateComponent } from './update/doctors-unit-update.component';
import { DoctorsUnitDeleteDialogComponent } from './delete/doctors-unit-delete-dialog.component';
import { DoctorsUnitRoutingModule } from './route/doctors-unit-routing.module';

@NgModule({
  imports: [SharedModule, DoctorsUnitRoutingModule],
  declarations: [DoctorsUnitComponent, DoctorsUnitDetailComponent, DoctorsUnitUpdateComponent, DoctorsUnitDeleteDialogComponent],
})
export class DoctorsUnitModule {}
