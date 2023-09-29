import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ExtraPassUnitFormService, ExtraPassUnitFormGroup } from './extra-pass-unit-form.service';
import { IExtraPassUnit } from '../extra-pass-unit.model';
import { ExtraPassUnitService } from '../service/extra-pass-unit.service';

@Component({
  selector: 'jhi-extra-pass-unit-update',
  templateUrl: './extra-pass-unit-update.component.html',
})
export class ExtraPassUnitUpdateComponent implements OnInit {
  isSaving = false;
  extraPassUnit: IExtraPassUnit | null = null;

  editForm: ExtraPassUnitFormGroup = this.extraPassUnitFormService.createExtraPassUnitFormGroup();

  constructor(
    protected extraPassUnitService: ExtraPassUnitService,
    protected extraPassUnitFormService: ExtraPassUnitFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extraPassUnit }) => {
      this.extraPassUnit = extraPassUnit;
      if (extraPassUnit) {
        this.updateForm(extraPassUnit);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const extraPassUnit = this.extraPassUnitFormService.getExtraPassUnit(this.editForm);
    if (extraPassUnit.id !== null) {
      this.subscribeToSaveResponse(this.extraPassUnitService.update(extraPassUnit));
    } else {
      this.subscribeToSaveResponse(this.extraPassUnitService.create(extraPassUnit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtraPassUnit>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(extraPassUnit: IExtraPassUnit): void {
    this.extraPassUnit = extraPassUnit;
    this.extraPassUnitFormService.resetForm(this.editForm, extraPassUnit);
  }
}
