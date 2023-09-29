import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DoctorsFormService } from './doctors-form.service';
import { DoctorsService } from '../service/doctors.service';
import { IDoctors } from '../doctors.model';
import { IDoctorsUnit } from 'app/entities/doctors-unit/doctors-unit.model';
import { DoctorsUnitService } from 'app/entities/doctors-unit/service/doctors-unit.service';
import { IExtraPassUnit } from 'app/entities/extra-pass-unit/extra-pass-unit.model';
import { ExtraPassUnitService } from 'app/entities/extra-pass-unit/service/extra-pass-unit.service';

import { DoctorsUpdateComponent } from './doctors-update.component';

describe('Doctors Management Update Component', () => {
  let comp: DoctorsUpdateComponent;
  let fixture: ComponentFixture<DoctorsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let doctorsFormService: DoctorsFormService;
  let doctorsService: DoctorsService;
  let doctorsUnitService: DoctorsUnitService;
  let extraPassUnitService: ExtraPassUnitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DoctorsUpdateComponent],
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
      .overrideTemplate(DoctorsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DoctorsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    doctorsFormService = TestBed.inject(DoctorsFormService);
    doctorsService = TestBed.inject(DoctorsService);
    doctorsUnitService = TestBed.inject(DoctorsUnitService);
    extraPassUnitService = TestBed.inject(ExtraPassUnitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DoctorsUnit query and add missing value', () => {
      const doctors: IDoctors = { id: 456 };
      const doctorsUnit: IDoctorsUnit = { id: 23495 };
      doctors.doctorsUnit = doctorsUnit;

      const doctorsUnitCollection: IDoctorsUnit[] = [{ id: 3083 }];
      jest.spyOn(doctorsUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: doctorsUnitCollection })));
      const additionalDoctorsUnits = [doctorsUnit];
      const expectedCollection: IDoctorsUnit[] = [...additionalDoctorsUnits, ...doctorsUnitCollection];
      jest.spyOn(doctorsUnitService, 'addDoctorsUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ doctors });
      comp.ngOnInit();

      expect(doctorsUnitService.query).toHaveBeenCalled();
      expect(doctorsUnitService.addDoctorsUnitToCollectionIfMissing).toHaveBeenCalledWith(
        doctorsUnitCollection,
        ...additionalDoctorsUnits.map(expect.objectContaining)
      );
      expect(comp.doctorsUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ExtraPassUnit query and add missing value', () => {
      const doctors: IDoctors = { id: 456 };
      const extraPassUnit: IExtraPassUnit = { id: 35876 };
      doctors.extraPassUnit = extraPassUnit;

      const extraPassUnitCollection: IExtraPassUnit[] = [{ id: 90419 }];
      jest.spyOn(extraPassUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: extraPassUnitCollection })));
      const additionalExtraPassUnits = [extraPassUnit];
      const expectedCollection: IExtraPassUnit[] = [...additionalExtraPassUnits, ...extraPassUnitCollection];
      jest.spyOn(extraPassUnitService, 'addExtraPassUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ doctors });
      comp.ngOnInit();

      expect(extraPassUnitService.query).toHaveBeenCalled();
      expect(extraPassUnitService.addExtraPassUnitToCollectionIfMissing).toHaveBeenCalledWith(
        extraPassUnitCollection,
        ...additionalExtraPassUnits.map(expect.objectContaining)
      );
      expect(comp.extraPassUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const doctors: IDoctors = { id: 456 };
      const doctorsUnit: IDoctorsUnit = { id: 53965 };
      doctors.doctorsUnit = doctorsUnit;
      const extraPassUnit: IExtraPassUnit = { id: 83901 };
      doctors.extraPassUnit = extraPassUnit;

      activatedRoute.data = of({ doctors });
      comp.ngOnInit();

      expect(comp.doctorsUnitsSharedCollection).toContain(doctorsUnit);
      expect(comp.extraPassUnitsSharedCollection).toContain(extraPassUnit);
      expect(comp.doctors).toEqual(doctors);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctors>>();
      const doctors = { id: 123 };
      jest.spyOn(doctorsFormService, 'getDoctors').mockReturnValue(doctors);
      jest.spyOn(doctorsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctors });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: doctors }));
      saveSubject.complete();

      // THEN
      expect(doctorsFormService.getDoctors).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(doctorsService.update).toHaveBeenCalledWith(expect.objectContaining(doctors));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctors>>();
      const doctors = { id: 123 };
      jest.spyOn(doctorsFormService, 'getDoctors').mockReturnValue({ id: null });
      jest.spyOn(doctorsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctors: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: doctors }));
      saveSubject.complete();

      // THEN
      expect(doctorsFormService.getDoctors).toHaveBeenCalled();
      expect(doctorsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctors>>();
      const doctors = { id: 123 };
      jest.spyOn(doctorsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctors });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(doctorsService.update).toHaveBeenCalled();
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
