import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoctorsUnit } from '../doctors-unit.model';
import { DoctorsUnitService } from '../service/doctors-unit.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './doctors-unit-delete-dialog.component.html',
})
export class DoctorsUnitDeleteDialogComponent {
  doctorsUnit?: IDoctorsUnit;

  constructor(protected doctorsUnitService: DoctorsUnitService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.doctorsUnitService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
