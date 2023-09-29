import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UnitImgsComponent } from './list/unit-imgs.component';
import { UnitImgsDetailComponent } from './detail/unit-imgs-detail.component';
import { UnitImgsUpdateComponent } from './update/unit-imgs-update.component';
import { UnitImgsDeleteDialogComponent } from './delete/unit-imgs-delete-dialog.component';
import { UnitImgsRoutingModule } from './route/unit-imgs-routing.module';

@NgModule({
  imports: [SharedModule, UnitImgsRoutingModule],
  declarations: [UnitImgsComponent, UnitImgsDetailComponent, UnitImgsUpdateComponent, UnitImgsDeleteDialogComponent],
})
export class UnitImgsModule {}
