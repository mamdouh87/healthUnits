import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DoctorPassImgsFormService, DoctorPassImgsFormGroup } from './doctor-pass-imgs-form.service';
import { IDoctorPassImgs } from '../doctor-pass-imgs.model';
import { DoctorPassImgsService } from '../service/doctor-pass-imgs.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUnits } from 'app/entities/units/units.model';
import { UnitsService } from 'app/entities/units/service/units.service';
import { IDoctors } from 'app/entities/doctors/doctors.model';
import { DoctorsService } from 'app/entities/doctors/service/doctors.service';

@Component({
  selector: 'jhi-doctor-pass-imgs-update',
  templateUrl: './doctor-pass-imgs-update.component.html',
})
export class DoctorPassImgsUpdateComponent implements OnInit {
  isSaving = false;
  doctorPassImgs: IDoctorPassImgs | null = null;

  unitsSharedCollection: IUnits[] = [];
  doctorsSharedCollection: IDoctors[] = [];

  editForm: DoctorPassImgsFormGroup = this.doctorPassImgsFormService.createDoctorPassImgsFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected doctorPassImgsService: DoctorPassImgsService,
    protected doctorPassImgsFormService: DoctorPassImgsFormService,
    protected unitsService: UnitsService,
    protected doctorsService: DoctorsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUnits = (o1: IUnits | null, o2: IUnits | null): boolean => this.unitsService.compareUnits(o1, o2);

  compareDoctors = (o1: IDoctors | null, o2: IDoctors | null): boolean => this.doctorsService.compareDoctors(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctorPassImgs }) => {
      this.doctorPassImgs = doctorPassImgs;
      if (doctorPassImgs) {
        this.updateForm(doctorPassImgs);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('healthUnitApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const doctorPassImgs = this.doctorPassImgsFormService.getDoctorPassImgs(this.editForm);
    if (doctorPassImgs.id !== null) {
      this.subscribeToSaveResponse(this.doctorPassImgsService.update(doctorPassImgs));
    } else {
      this.subscribeToSaveResponse(this.doctorPassImgsService.create(doctorPassImgs));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctorPassImgs>>): void {
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

  protected updateForm(doctorPassImgs: IDoctorPassImgs): void {
    this.doctorPassImgs = doctorPassImgs;
    this.doctorPassImgsFormService.resetForm(this.editForm, doctorPassImgs);

    this.unitsSharedCollection = this.unitsService.addUnitsToCollectionIfMissing<IUnits>(this.unitsSharedCollection, doctorPassImgs.unit);
    this.doctorsSharedCollection = this.doctorsService.addDoctorsToCollectionIfMissing<IDoctors>(
      this.doctorsSharedCollection,
      doctorPassImgs.doctor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.unitsService
      .query()
      .pipe(map((res: HttpResponse<IUnits[]>) => res.body ?? []))
      .pipe(map((units: IUnits[]) => this.unitsService.addUnitsToCollectionIfMissing<IUnits>(units, this.doctorPassImgs?.unit)))
      .subscribe((units: IUnits[]) => (this.unitsSharedCollection = units));

    this.doctorsService
      .query()
      .pipe(map((res: HttpResponse<IDoctors[]>) => res.body ?? []))
      .pipe(
        map((doctors: IDoctors[]) => this.doctorsService.addDoctorsToCollectionIfMissing<IDoctors>(doctors, this.doctorPassImgs?.doctor))
      )
      .subscribe((doctors: IDoctors[]) => (this.doctorsSharedCollection = doctors));
  }
}
