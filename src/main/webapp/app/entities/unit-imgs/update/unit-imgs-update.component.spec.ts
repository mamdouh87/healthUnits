import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UnitImgsFormService } from './unit-imgs-form.service';
import { UnitImgsService } from '../service/unit-imgs.service';
import { IUnitImgs } from '../unit-imgs.model';
import { IUnits } from 'app/entities/units/units.model';
import { UnitsService } from 'app/entities/units/service/units.service';

import { UnitImgsUpdateComponent } from './unit-imgs-update.component';

describe('UnitImgs Management Update Component', () => {
  let comp: UnitImgsUpdateComponent;
  let fixture: ComponentFixture<UnitImgsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let unitImgsFormService: UnitImgsFormService;
  let unitImgsService: UnitImgsService;
  let unitsService: UnitsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UnitImgsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(UnitImgsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UnitImgsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    unitImgsFormService = TestBed.inject(UnitImgsFormService);
    unitImgsService = TestBed.inject(UnitImgsService);
    unitsService = TestBed.inject(UnitsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Units query and add missing value', () => {
      const unitImgs: IUnitImgs = { id: 456 };
      const unit: IUnits = { id: 48635 };
      unitImgs.unit = unit;

      const unitsCollection: IUnits[] = [{ id: 33791 }];
      jest.spyOn(unitsService, 'query').mockReturnValue(of(new HttpResponse({ body: unitsCollection })));
      const additionalUnits = [unit];
      const expectedCollection: IUnits[] = [...additionalUnits, ...unitsCollection];
      jest.spyOn(unitsService, 'addUnitsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ unitImgs });
      comp.ngOnInit();

      expect(unitsService.query).toHaveBeenCalled();
      expect(unitsService.addUnitsToCollectionIfMissing).toHaveBeenCalledWith(
        unitsCollection,
        ...additionalUnits.map(expect.objectContaining)
      );
      expect(comp.unitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const unitImgs: IUnitImgs = { id: 456 };
      const unit: IUnits = { id: 91238 };
      unitImgs.unit = unit;

      activatedRoute.data = of({ unitImgs });
      comp.ngOnInit();

      expect(comp.unitsSharedCollection).toContain(unit);
      expect(comp.unitImgs).toEqual(unitImgs);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUnitImgs>>();
      const unitImgs = { id: 123 };
      jest.spyOn(unitImgsFormService, 'getUnitImgs').mockReturnValue(unitImgs);
      jest.spyOn(unitImgsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ unitImgs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: unitImgs }));
      saveSubject.complete();

      // THEN
      expect(unitImgsFormService.getUnitImgs).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(unitImgsService.update).toHaveBeenCalledWith(expect.objectContaining(unitImgs));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUnitImgs>>();
      const unitImgs = { id: 123 };
      jest.spyOn(unitImgsFormService, 'getUnitImgs').mockReturnValue({ id: null });
      jest.spyOn(unitImgsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ unitImgs: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: unitImgs }));
      saveSubject.complete();

      // THEN
      expect(unitImgsFormService.getUnitImgs).toHaveBeenCalled();
      expect(unitImgsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUnitImgs>>();
      const unitImgs = { id: 123 };
      jest.spyOn(unitImgsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ unitImgs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(unitImgsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUnits', () => {
      it('Should forward to unitsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(unitsService, 'compareUnits');
        comp.compareUnits(entity, entity2);
        expect(unitsService.compareUnits).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
