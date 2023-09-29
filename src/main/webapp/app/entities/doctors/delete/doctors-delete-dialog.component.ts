import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoctors } from '../doctors.model';
import { DoctorsService } from '../service/doctors.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './doctors-delete-dialog.component.html',
})
export class DoctorsDeleteDialogComponent {
  doctors?: IDoctors;

  constructor(protected doctorsService: DoctorsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.doctorsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
