import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoctorPassImgs } from '../doctor-pass-imgs.model';
import { DoctorPassImgsService } from '../service/doctor-pass-imgs.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './doctor-pass-imgs-delete-dialog.component.html',
})
export class DoctorPassImgsDeleteDialogComponent {
  doctorPassImgs?: IDoctorPassImgs;

  constructor(protected doctorPassImgsService: DoctorPassImgsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.doctorPassImgsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
