import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWeekDayDate } from '../week-day-date.model';
import { WeekDayDateService } from '../service/week-day-date.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './week-day-date-delete-dialog.component.html',
})
export class WeekDayDateDeleteDialogComponent {
  weekDayDate?: IWeekDayDate;

  constructor(protected weekDayDateService: WeekDayDateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.weekDayDateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
