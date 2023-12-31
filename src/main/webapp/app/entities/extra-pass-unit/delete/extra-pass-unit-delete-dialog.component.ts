import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExtraPassUnit } from '../extra-pass-unit.model';
import { ExtraPassUnitService } from '../service/extra-pass-unit.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './extra-pass-unit-delete-dialog.component.html',
})
export class ExtraPassUnitDeleteDialogComponent {
  extraPassUnit?: IExtraPassUnit;

  constructor(protected extraPassUnitService: ExtraPassUnitService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.extraPassUnitService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
