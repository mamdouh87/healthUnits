import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WeekDayDateFormService } from './week-day-date-form.service';
import { WeekDayDateService } from '../service/week-day-date.service';
import { IWeekDayDate } from '../week-day-date.model';

import { WeekDayDateUpdateComponent } from './week-day-date-update.component';

describe('WeekDayDate Management Update Component', () => {
  let comp: WeekDayDateUpdateComponent;
  let fixture: ComponentFixture<WeekDayDateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let weekDayDateFormService: WeekDayDateFormService;
  let weekDayDateService: WeekDayDateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WeekDayDateUpdateComponent],
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
      .overrideTemplate(WeekDayDateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WeekDayDateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    weekDayDateFormService = TestBed.inject(WeekDayDateFormService);
    weekDayDateService = TestBed.inject(WeekDayDateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const weekDayDate: IWeekDayDate = { id: 456 };

      activatedRoute.data = of({ weekDayDate });
      comp.ngOnInit();

      expect(comp.weekDayDate).toEqual(weekDayDate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWeekDayDate>>();
      const weekDayDate = { id: 123 };
      jest.spyOn(weekDayDateFormService, 'getWeekDayDate').mockReturnValue(weekDayDate);
      jest.spyOn(weekDayDateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ weekDayDate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: weekDayDate }));
      saveSubject.complete();

      // THEN
      expect(weekDayDateFormService.getWeekDayDate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(weekDayDateService.update).toHaveBeenCalledWith(expect.objectContaining(weekDayDate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWeekDayDate>>();
      const weekDayDate = { id: 123 };
      jest.spyOn(weekDayDateFormService, 'getWeekDayDate').mockReturnValue({ id: null });
      jest.spyOn(weekDayDateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ weekDayDate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: weekDayDate }));
      saveSubject.complete();

      // THEN
      expect(weekDayDateFormService.getWeekDayDate).toHaveBeenCalled();
      expect(weekDayDateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWeekDayDate>>();
      const weekDayDate = { id: 123 };
      jest.spyOn(weekDayDateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ weekDayDate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(weekDayDateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
