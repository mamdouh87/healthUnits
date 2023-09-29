import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DoctorsFormService, DoctorsFormGroup } from './doctors-form.service';
import { IDoctors } from '../doctors.model';
import { DoctorsService } from '../service/doctors.service';
import { IDoctorsUnit } from 'app/entities/doctors-unit/doctors-unit.model';
import { DoctorsUnitService } from 'app/entities/doctors-unit/service/doctors-unit.service';
import { IExtraPassUnit } from 'app/entities/extra-pass-unit/extra-pass-unit.model';
import { ExtraPassUnitService } from 'app/entities/extra-pass-unit/service/extra-pass-unit.service';

@Component({
  selector: 'jhi-doctors-update',
  templateUrl: './doctors-update.component.html',
})
export class DoctorsUpdateComponent implements OnInit {
  isSaving = false;
  doctors: IDoctors | null = null;

  doctorsUnitsSharedCollection: IDoctorsUnit[] = [];
  extraPassUnitsSharedCollection: IExtraPassUnit[] = [];

  editForm: DoctorsFormGroup = this.doctorsFormService.createDoctorsFormGroup();

  constructor(
    protected doctorsService: DoctorsService,
    protected doctorsFormService: DoctorsFormService,
    protected doctorsUnitService: DoctorsUnitService,
    protected extraPassUnitService: ExtraPassUnitService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDoctorsUnit = (o1: IDoctorsUnit | null, o2: IDoctorsUnit | null): boolean => this.doctorsUnitService.compareDoctorsUnit(o1, o2);

  compareExtraPassUnit = (o1: IExtraPassUnit | null, o2: IExtraPassUnit | null): boolean =>
    this.extraPassUnitService.compareExtraPassUnit(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctors }) => {
      this.doctors = doctors;
      if (doctors) {
        this.updateForm(doctors);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const doctors = this.doctorsFormService.getDoctors(this.editForm);
    if (doctors.id !== null) {
      this.subscribeToSaveResponse(this.doctorsService.update(doctors));
    } else {
      this.subscribeToSaveResponse(this.doctorsService.create(doctors));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctors>>): void {
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

  protected updateForm(doctors: IDoctors): void {
    this.doctors = doctors;
    this.doctorsFormService.resetForm(this.editForm, doctors);

    this.doctorsUnitsSharedCollection = this.doctorsUnitService.addDoctorsUnitToCollectionIfMissing<IDoctorsUnit>(
      this.doctorsUnitsSharedCollection,
      doctors.doctorsUnit
    );
    this.extraPassUnitsSharedCollection = this.extraPassUnitService.addExtraPassUnitToCollectionIfMissing<IExtraPassUnit>(
      this.extraPassUnitsSharedCollection,
      doctors.extraPassUnit
    );
  }

  protected loadRelationshipsOptions(): void {
    this.doctorsUnitService
      .query()
      .pipe(map((res: HttpResponse<IDoctorsUnit[]>) => res.body ?? []))
      .pipe(
        map((doctorsUnits: IDoctorsUnit[]) =>
          this.doctorsUnitService.addDoctorsUnitToCollectionIfMissing<IDoctorsUnit>(doctorsUnits, this.doctors?.doctorsUnit)
        )
      )
      .subscribe((doctorsUnits: IDoctorsUnit[]) => (this.doctorsUnitsSharedCollection = doctorsUnits));

    this.extraPassUnitService
      .query()
      .pipe(map((res: HttpResponse<IExtraPassUnit[]>) => res.body ?? []))
      .pipe(
        map((extraPassUnits: IExtraPassUnit[]) =>
          this.extraPassUnitService.addExtraPassUnitToCollectionIfMissing<IExtraPassUnit>(extraPassUnits, this.doctors?.extraPassUnit)
        )
      )
      .subscribe((extraPassUnits: IExtraPassUnit[]) => (this.extraPassUnitsSharedCollection = extraPassUnits));
  }
}
