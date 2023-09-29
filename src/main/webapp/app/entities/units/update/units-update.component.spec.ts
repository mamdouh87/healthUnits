import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UnitsFormService } from './units-form.service';
import { UnitsService } from '../service/units.service';
import { IUnits } from '../units.model';
import { IDoctorsUnit } from 'app/entities/doctors-unit/doctors-unit.model';
import { DoctorsUnitService } from 'app/entities/doctors-unit/service/doctors-unit.service';
import { IExtraPassUnit } from 'app/entities/extra-pass-unit/extra-pass-unit.model';
import { ExtraPassUnitService } from 'app/entities/extra-pass-unit/service/extra-pass-unit.service';

import { UnitsUpdateComponent } from './units-update.component';

describe('Units Management Update Component', () => {
  let comp: UnitsUpdateComponent;
  let fixture: ComponentFixture<UnitsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let unitsFormService: UnitsFormService;
  let unitsService: UnitsService;
  let doctorsUnitService: DoctorsUnitService;
  let extraPassUnitService: ExtraPassUnitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UnitsUpdateComponent],
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
      .overrideTemplate(UnitsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UnitsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    unitsFormService = TestBed.inject(UnitsFormService);
    unitsService = TestBed.inject(UnitsService);
    doctorsUnitService = TestBed.inject(DoctorsUnitService);
    extraPassUnitService = TestBed.inject(ExtraPassUnitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DoctorsUnit query and add missing value', () => {
      const units: IUnits = { id: 456 };
      const doctorsUnit: IDoctorsUnit = { id: 83649 };
      units.doctorsUnit = doctorsUnit;

      const doctorsUnitCollection: IDoctorsUnit[] = [{ id: 35318 }];
      jest.spyOn(doctorsUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: doctorsUnitCollection })));
      const additionalDoctorsUnits = [doctorsUnit];
      const expectedCollection: IDoctorsUnit[] = [...additionalDoctorsUnits, ...doctorsUnitCollection];
      jest.spyOn(doctorsUnitService, 'addDoctorsUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ units });
      comp.ngOnInit();

      expect(doctorsUnitService.query).toHaveBeenCalled();
      expect(doctorsUnitService.addDoctorsUnitToCollectionIfMissing).toHaveBeenCalledWith(
        doctorsUnitCollection,
        ...additionalDoctorsUnits.map(expect.objectContaining)
      );
      expect(comp.doctorsUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ExtraPassUnit query and add missing value', () => {
      const units: IUnits = { id: 456 };
      const extraPassUnit: IExtraPassUnit = { id: 10761 };
      units.extraPassUnit = extraPassUnit;

      const extraPassUnitCollection: IExtraPassUnit[] = [{ id: 69565 }];
      jest.spyOn(extraPassUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: extraPassUnitCollection })));
      const additionalExtraPassUnits = [extraPassUnit];
      const expectedCollection: IExtraPassUnit[] = [...additionalExtraPassUnits, ...extraPassUnitCollection];
      jest.spyOn(extraPassUnitService, 'addExtraPassUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ units });
      comp.ngOnInit();

      expect(extraPassUnitService.query).toHaveBeenCalled();
      expect(extraPassUnitService.addExtraPassUnitToCollectionIfMissing).toHaveBeenCalledWith(
        extraPassUnitCollection,
        ...additionalExtraPassUnits.map(expect.objectContaining)
      );
      expect(comp.extraPassUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const units: IUnits = { id: 456 };
      const doctorsUnit: IDoctorsUnit = { id: 25085 };
      units.doctorsUnit = doctorsUnit;
      const extraPassUnit: IExtraPassUnit = { id: 6112 };
      units.extraPassUnit = extraPassUnit;

      activatedRoute.data = of({ units });
      comp.ngOnInit();

      expect(comp.doctorsUnitsSharedCollection).toContain(doctorsUnit);
      expect(comp.extraPassUnitsSharedCollection).toContain(extraPassUnit);
      expect(comp.units).toEqual(units);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUnits>>();
      const units = { id: 123 };
      jest.spyOn(unitsFormService, 'getUnits').mockReturnValue(units);
      jest.spyOn(unitsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ units });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: units }));
      saveSubject.complete();

      // THEN
      expect(unitsFormService.getUnits).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(unitsService.update).toHaveBeenCalledWith(expect.objectContaining(units));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUnits>>();
      const units = { id: 123 };
      jest.spyOn(unitsFormService, 'getUnits').mockReturnValue({ id: null });
      jest.spyOn(unitsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ units: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: units }));
      saveSubject.complete();

      // THEN
      expect(unitsFormService.getUnits).toHaveBeenCalled();
      expect(unitsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUnits>>();
      const units = { id: 123 };
      jest.spyOn(unitsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ units });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(unitsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDoctorsUnit', () => {
      it('Should forward to doctorsUnitService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(doctorsUnitService, 'compareDoctorsUnit');
        comp.compareDoctorsUnit(entity, entity2);
        expect(doctorsUnitService.compareDoctorsUnit).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareExtraPassUnit', () => {
      it('Should forward to extraPassUnitService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(extraPassUnitService, 'compareExtraPassUnit');
        comp.compareExtraPassUnit(entity, entity2);
        expect(extraPassUnitService.compareExtraPassUnit).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
