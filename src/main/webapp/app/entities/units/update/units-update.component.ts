import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { UnitsFormService, UnitsFormGroup } from './units-form.service';
import { IUnits } from '../units.model';
import { UnitsService } from '../service/units.service';
import { IDoctorsUnit } from 'app/entities/doctors-unit/doctors-unit.model';
import { DoctorsUnitService } from 'app/entities/doctors-unit/service/doctors-unit.service';
import { IExtraPassUnit } from 'app/entities/extra-pass-unit/extra-pass-unit.model';
import { ExtraPassUnitService } from 'app/entities/extra-pass-unit/service/extra-pass-unit.service';

@Component({
  selector: 'jhi-units-update',
  templateUrl: './units-update.component.html',
})
export class UnitsUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnits | null = null;

  doctorsUnitsSharedCollection: IDoctorsUnit[] = [];
  extraPassUnitsSharedCollection: IExtraPassUnit[] = [];

  editForm: UnitsFormGroup = this.unitsFormService.createUnitsFormGroup();

  constructor(
    protected unitsService: UnitsService,
    protected unitsFormService: UnitsFormService,
    protected doctorsUnitService: DoctorsUnitService,
    protected extraPassUnitService: ExtraPassUnitService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDoctorsUnit = (o1: IDoctorsUnit | null, o2: IDoctorsUnit | null): boolean => this.doctorsUnitService.compareDoctorsUnit(o1, o2);

  compareExtraPassUnit = (o1: IExtraPassUnit | null, o2: IExtraPassUnit | null): boolean =>
    this.extraPassUnitService.compareExtraPassUnit(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ units }) => {
      this.units = units;
      if (units) {
        this.updateForm(units);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const units = this.unitsFormService.getUnits(this.editForm);
    if (units.id !== null) {
      this.subscribeToSaveResponse(this.unitsService.update(units));
    } else {
      this.subscribeToSaveResponse(this.unitsService.create(units));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnits>>): void {
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

  protected updateForm(units: IUnits): void {
    this.units = units;
    this.unitsFormService.resetForm(this.editForm, units);

    this.doctorsUnitsSharedCollection = this.doctorsUnitService.addDoctorsUnitToCollectionIfMissing<IDoctorsUnit>(
      this.doctorsUnitsSharedCollection,
      units.doctorsUnit
    );
    this.extraPassUnitsSharedCollection = this.extraPassUnitService.addExtraPassUnitToCollectionIfMissing<IExtraPassUnit>(
      this.extraPassUnitsSharedCollection,
      units.extraPassUnit
    );
  }

  protected loadRelationshipsOptions(): void {
    this.doctorsUnitService
      .query()
      .pipe(map((res: HttpResponse<IDoctorsUnit[]>) => res.body ?? []))
      .pipe(
        map((doctorsUnits: IDoctorsUnit[]) =>
          this.doctorsUnitService.addDoctorsUnitToCollectionIfMissing<IDoctorsUnit>(doctorsUnits, this.units?.doctorsUnit)
        )
      )
      .subscribe((doctorsUnits: IDoctorsUnit[]) => (this.doctorsUnitsSharedCollection = doctorsUnits));

    this.extraPassUnitService
      .query()
      .pipe(map((res: HttpResponse<IExtraPassUnit[]>) => res.body ?? []))
      .pipe(
        map((extraPassUnits: IExtraPassUnit[]) =>
          this.extraPassUnitService.addExtraPassUnitToCollectionIfMissing<IExtraPassUnit>(extraPassUnits, this.units?.extraPassUnit)
        )
      )
      .subscribe((extraPassUnits: IExtraPassUnit[]) => (this.extraPassUnitsSharedCollection = extraPassUnits));
  }
}
