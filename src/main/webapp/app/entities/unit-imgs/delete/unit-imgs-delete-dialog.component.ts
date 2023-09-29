import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnitImgs } from '../unit-imgs.model';
import { UnitImgsService } from '../service/unit-imgs.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './unit-imgs-delete-dialog.component.html',
})
export class UnitImgsDeleteDialogComponent {
  unitImgs?: IUnitImgs;

  constructor(protected unitImgsService: UnitImgsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.unitImgsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
