import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WeekDayDateComponent } from './list/week-day-date.component';
import { WeekDayDateDetailComponent } from './detail/week-day-date-detail.component';
import { WeekDayDateUpdateComponent } from './update/week-day-date-update.component';
import { WeekDayDateDeleteDialogComponent } from './delete/week-day-date-delete-dialog.component';
import { WeekDayDateRoutingModule } from './route/week-day-date-routing.module';

@NgModule({
  imports: [SharedModule, WeekDayDateRoutingModule],
  declarations: [WeekDayDateComponent, WeekDayDateDetailComponent, WeekDayDateUpdateComponent, WeekDayDateDeleteDialogComponent],
})
export class WeekDayDateModule {}
