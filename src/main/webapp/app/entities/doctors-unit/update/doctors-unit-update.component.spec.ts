import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DoctorsUnitFormService } from './doctors-unit-form.service';
import { DoctorsUnitService } from '../service/doctors-unit.service';
import { IDoctorsUnit } from '../doctors-unit.model';

import { DoctorsUnitUpdateComponent } from './doctors-unit-update.component';

describe('DoctorsUnit Management Update Component', () => {
  let comp: DoctorsUnitUpdateComponent;
  let fixture: ComponentFixture<DoctorsUnitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let doctorsUnitFormService: DoctorsUnitFormService;
  let doctorsUnitService: DoctorsUnitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DoctorsUnitUpdateComponent],
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
      .overrideTemplate(DoctorsUnitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DoctorsUnitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    doctorsUnitFormService = TestBed.inject(DoctorsUnitFormService);
    doctorsUnitService = TestBed.inject(DoctorsUnitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const doctorsUnit: IDoctorsUnit = { id: 456 };

      activatedRoute.data = of({ doctorsUnit });
      comp.ngOnInit();

      expect(comp.doctorsUnit).toEqual(doctorsUnit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctorsUnit>>();
      const doctorsUnit = { id: 123 };
      jest.spyOn(doctorsUnitFormService, 'getDoctorsUnit').mockReturnValue(doctorsUnit);
      jest.spyOn(doctorsUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctorsUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: doctorsUnit }));
      saveSubject.complete();

      // THEN
      expect(doctorsUnitFormService.getDoctorsUnit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(doctorsUnitService.update).toHaveBeenCalledWith(expect.objectContaining(doctorsUnit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctorsUnit>>();
      const doctorsUnit = { id: 123 };
      jest.spyOn(doctorsUnitFormService, 'getDoctorsUnit').mockReturnValue({ id: null });
      jest.spyOn(doctorsUnitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctorsUnit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: doctorsUnit }));
      saveSubject.complete();

      // THEN
      expect(doctorsUnitFormService.getDoctorsUnit).toHaveBeenCalled();
      expect(doctorsUnitService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDoctorsUnit>>();
      const doctorsUnit = { id: 123 };
      jest.spyOn(doctorsUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ doctorsUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(doctorsUnitService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
