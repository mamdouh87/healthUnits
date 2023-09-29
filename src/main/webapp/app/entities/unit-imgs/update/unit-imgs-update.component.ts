import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { UnitImgsFormService, UnitImgsFormGroup } from './unit-imgs-form.service';
import { IUnitImgs } from '../unit-imgs.model';
import { UnitImgsService } from '../service/unit-imgs.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUnits } from 'app/entities/units/units.model';
import { UnitsService } from 'app/entities/units/service/units.service';

@Component({
  selector: 'jhi-unit-imgs-update',
  templateUrl: './unit-imgs-update.component.html',
})
export class UnitImgsUpdateComponent implements OnInit {
  isSaving = false;
  unitImgs: IUnitImgs | null = null;

  unitsSharedCollection: IUnits[] = [];

  editForm: UnitImgsFormGroup = this.unitImgsFormService.createUnitImgsFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected unitImgsService: UnitImgsService,
    protected unitImgsFormService: UnitImgsFormService,
    protected unitsService: UnitsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUnits = (o1: IUnits | null, o2: IUnits | null): boolean => this.unitsService.compareUnits(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitImgs }) => {
      this.unitImgs = unitImgs;
      if (unitImgs) {
        this.updateForm(unitImgs);
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
    const unitImgs = this.unitImgsFormService.getUnitImgs(this.editForm);
    if (unitImgs.id !== null) {
      this.subscribeToSaveResponse(this.unitImgsService.update(unitImgs));
    } else {
      this.subscribeToSaveResponse(this.unitImgsService.create(unitImgs));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnitImgs>>): void {
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

  protected updateForm(unitImgs: IUnitImgs): void {
    this.unitImgs = unitImgs;
    this.unitImgsFormService.resetForm(this.editForm, unitImgs);

    this.unitsSharedCollection = this.unitsService.addUnitsToCollectionIfMissing<IUnits>(this.unitsSharedCollection, unitImgs.unit);
  }

  protected loadRelationshipsOptions(): void {
    this.unitsService
      .query()
      .pipe(map((res: HttpResponse<IUnits[]>) => res.body ?? []))
      .pipe(map((units: IUnits[]) => this.unitsService.addUnitsToCollectionIfMissing<IUnits>(units, this.unitImgs?.unit)))
      .subscribe((units: IUnits[]) => (this.unitsSharedCollection = units));
  }
}
