import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DoctorsUnitFormService, DoctorsUnitFormGroup } from './doctors-unit-form.service';
import { IDoctorsUnit } from '../doctors-unit.model';
import { DoctorsUnitService } from '../service/doctors-unit.service';

@Component({
  selector: 'jhi-doctors-unit-update',
  templateUrl: './doctors-unit-update.component.html',
})
export class DoctorsUnitUpdateComponent implements OnInit {
  isSaving = false;
  doctorsUnit: IDoctorsUnit | null = null;

  editForm: DoctorsUnitFormGroup = this.doctorsUnitFormService.createDoctorsUnitFormGroup();

  constructor(
    protected doctorsUnitService: DoctorsUnitService,
    protected doctorsUnitFormService: DoctorsUnitFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctorsUnit }) => {
      this.doctorsUnit = doctorsUnit;
      if (doctorsUnit) {
        this.updateForm(doctorsUnit);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const doctorsUnit = this.doctorsUnitFormService.getDoctorsUnit(this.editForm);
    if (doctorsUnit.id !== null) {
      this.subscribeToSaveResponse(this.doctorsUnitService.update(doctorsUnit));
    } else {
      this.subscribeToSaveResponse(this.doctorsUnitService.create(doctorsUnit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctorsUnit>>): void {
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

  protected updateForm(doctorsUnit: IDoctorsUnit): void {
    this.doctorsUnit = doctorsUnit;
    this.doctorsUnitFormService.resetForm(this.editForm, doctorsUnit);
  }
}
