import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExtraPassUnitComponent } from './list/extra-pass-unit.component';
import { ExtraPassUnitDetailComponent } from './detail/extra-pass-unit-detail.component';
import { ExtraPassUnitUpdateComponent } from './update/extra-pass-unit-update.component';
import { ExtraPassUnitDeleteDialogComponent } from './delete/extra-pass-unit-delete-dialog.component';
import { ExtraPassUnitRoutingModule } from './route/extra-pass-unit-routing.module';

@NgModule({
  imports: [SharedModule, ExtraPassUnitRoutingModule],
  declarations: [ExtraPassUnitComponent, ExtraPassUnitDetailComponent, ExtraPassUnitUpdateComponent, ExtraPassUnitDeleteDialogComponent],
})
export class ExtraPassUnitModule {}
