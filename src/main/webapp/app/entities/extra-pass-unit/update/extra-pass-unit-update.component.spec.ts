import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExtraPassUnitFormService } from './extra-pass-unit-form.service';
import { ExtraPassUnitService } from '../service/extra-pass-unit.service';
import { IExtraPassUnit } from '../extra-pass-unit.model';

import { ExtraPassUnitUpdateComponent } from './extra-pass-unit-update.component';

describe('ExtraPassUnit Management Update Component', () => {
  let comp: ExtraPassUnitUpdateComponent;
  let fixture: ComponentFixture<ExtraPassUnitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let extraPassUnitFormService: ExtraPassUnitFormService;
  let extraPassUnitService: ExtraPassUnitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExtraPassUnitUpdateComponent],
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
      .overrideTemplate(ExtraPassUnitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExtraPassUnitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    extraPassUnitFormService = TestBed.inject(ExtraPassUnitFormService);
    extraPassUnitService = TestBed.inject(ExtraPassUnitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const extraPassUnit: IExtraPassUnit = { id: 456 };

      activatedRoute.data = of({ extraPassUnit });
      comp.ngOnInit();

      expect(comp.extraPassUnit).toEqual(extraPassUnit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExtraPassUnit>>();
      const extraPassUnit = { id: 123 };
      jest.spyOn(extraPassUnitFormService, 'getExtraPassUnit').mockReturnValue(extraPassUnit);
      jest.spyOn(extraPassUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ extraPassUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: extraPassUnit }));
      saveSubject.complete();

      // THEN
      expect(extraPassUnitFormService.getExtraPassUnit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(extraPassUnitService.update).toHaveBeenCalledWith(expect.objectContaining(extraPassUnit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExtraPassUnit>>();
      const extraPassUnit = { id: 123 };
      jest.spyOn(extraPassUnitFormService, 'getExtraPassUnit').mockReturnValue({ id: null });
      jest.spyOn(extraPassUnitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ extraPassUnit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: extraPassUnit }));
      saveSubject.complete();

      // THEN
      expect(extraPassUnitFormService.getExtraPassUnit).toHaveBeenCalled();
      expect(extraPassUnitService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExtraPassUnit>>();
      const extraPassUnit = { id: 123 };
      jest.spyOn(extraPassUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ extraPassUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(extraPassUnitService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
