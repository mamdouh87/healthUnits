import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DoctorPassImgsFormService } from './doctor-pass-imgs-form.service';
import { DoctorPassImgsService } from '../service/doctor-pass-imgs.service';
import { IDoctorPassImgs } from '../doctor-pass-imgs.model';
import { IUnits } from 'app/entities/units/units.model';
import { UnitsService } from 'app/entities/units/service/units.service';
import { IDoctors } from 'app/entities/doctors/doctors.model';
import { DoctorsService } from 'app/entities/doctors/service/doctors.service';

import { DoctorPassImgsUpdateComponent } from './doctor-pass-imgs-update.component';

describe('DoctorPassImgs Management Update Component', () => {
  let comp: DoctorPassImgsUpdateComponent;
  let fixture: ComponentFixture<DoctorPassImgsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let doctorPassImgsFormService: DoctorPassImgsFormService;
  let doctorPassImgsService: DoctorPassImgsService;
  let unitsService: UnitsService;
  let doctorsService: DoctorsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DoctorPassImgsUpdateComponent],
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
      .overrideTemplate(DoctorPassImgsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DoctorPassImgsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    doctorPassImgsFormService = TestBed.inject(DoctorPassImgsFormService);
    doctorPassImgsService = TestBed.inject(DoctorPassImgsService);
    unitsService = TestBed.inject(UnitsService);
    doctorsService = TestBed.inject(DoctorsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Units query and add missing value', () => {
      const doctorPassImgs: IDoctorPassImgs = { id: 456 };
      const unit: IUnits = { id: 81953 };
      doctorPassImgs.unit = unit;

      const unitsCollection: IUnits[] = [{ id: 51344 }];
      jest.spyOn(unitsService, 'query').mockReturnValue(of(new HttpResponse({ body: unitsCollection })));
      const additionalUnits = [unit];
      const expectedCollection: IUnits[] = [...additionalUnits, ...unitsCollection];
      jest.spyOn(unitsService, 'addUnitsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ doctorPassImgs });
      comp.ngOnInit();

      expect(unitsService.query).toHaveBeenCalled();
      expect(unitsService.addUnitsToCollectionIfMissing).toHaveBeenCalledWith(
        unitsCollection,
        ...additionalUnits.map(expect.objectContaining)
      );
      expect(comp.unitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Doctors query and add missing value', () => {
      const doctorPassImgs: IDoctorPassImgs = { id: 456 };
      const doctor: IDoctors = { id: 17365 };
      doctorPassImgs.doctor = doctor;

      const doctorsCollection: IDoctors[] = [{ id: 8950 }];
      jest.spyOn(doctorsService, 'query').mockReturnValue(of(new HttpResponse({ body: doctorsCollection })));
      const additionalDoctors = [doctor];
      const expectedCollection: IDoctors[] = [...additionalDoctors, ...doctorsCollection];
      jest.spyOn(doctorsService, 'addDoctorsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ doctorPassImgs });
      comp.ngOnInit();

      expect(doctorsService.query).toHaveBeenCalled();
      expect(doctorsService.addDoctorsToCollectionIfMissing).toHaveBeenCalledWith(
        doctorsCollection,
        ...additionalDoctors.map(expect.objectContaining)
      );
      expect(comp.doctorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const doctorPassImgs: IDoctorPassImgs = { id: 456 };
      const unit: IUnits = { id: 58323 };
      doctorPassImgs.unit = unit;
      const doctor: IDoctors = { id: 85300 };
      doctorPassImgs.doctor = doctor;

      activatedRoute.data = of({ doctorPassImgs });
      comp.ngOnInit();

      expect(comp.unitsSharedCollection).toContain(unit);
      expect(comp.doctorsSharedCollection).toContain(doctor);
      expect(comp.doctorPassImgs).toEqual(doctorPassImgs);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctorPassImgs>>();
      const doctorPassImgs = { id: 123 };
      jest.spyOn(doctorPassImgsFormService, 'getDoctorPassImgs').mockReturnValue(doctorPassImgs);
      jest.spyOn(doctorPassImgsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctorPassImgs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: doctorPassImgs }));
      saveSubject.complete();

      // THEN
      expect(doctorPassImgsFormService.getDoctorPassImgs).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(doctorPassImgsService.update).toHaveBeenCalledWith(expect.objectContaining(doctorPassImgs));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctorPassImgs>>();
      const doctorPassImgs = { id: 123 };
      jest.spyOn(doctorPassImgsFormService, 'getDoctorPassImgs').mockReturnValue({ id: null });
      jest.spyOn(doctorPassImgsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctorPassImgs: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: doctorPassImgs }));
      saveSubject.complete();

      // THEN
      expect(doctorPassImgsFormService.getDoctorPassImgs).toHaveBeenCalled();
      expect(doctorPassImgsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctorPassImgs>>();
      const doctorPassImgs = { id: 123 };
      jest.spyOn(doctorPassImgsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctorPassImgs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(doctorPassImgsService.update).toHaveBeenCalled();
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

    describe('compareDoctors', () => {
      it('Should forward to doctorsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(doctorsService, 'compareDoctors');
        comp.compareDoctors(entity, entity2);
        expect(doctorsService.compareDoctors).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
