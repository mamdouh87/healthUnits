import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NotificationsFormService } from './notifications-form.service';
import { NotificationsService } from '../service/notifications.service';
import { INotifications } from '../notifications.model';
import { IUnits } from 'app/entities/units/units.model';
import { UnitsService } from 'app/entities/units/service/units.service';

import { NotificationsUpdateComponent } from './notifications-update.component';

describe('Notifications Management Update Component', () => {
  let comp: NotificationsUpdateComponent;
  let fixture: ComponentFixture<NotificationsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let notificationsFormService: NotificationsFormService;
  let notificationsService: NotificationsService;
  let unitsService: UnitsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NotificationsUpdateComponent],
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
      .overrideTemplate(NotificationsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NotificationsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    notificationsFormService = TestBed.inject(NotificationsFormService);
    notificationsService = TestBed.inject(NotificationsService);
    unitsService = TestBed.inject(UnitsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Units query and add missing value', () => {
      const notifications: INotifications = { id: 456 };
      const unit: IUnits = { id: 7792 };
      notifications.unit = unit;

      const unitsCollection: IUnits[] = [{ id: 21424 }];
      jest.spyOn(unitsService, 'query').mockReturnValue(of(new HttpResponse({ body: unitsCollection })));
      const additionalUnits = [unit];
      const expectedCollection: IUnits[] = [...additionalUnits, ...unitsCollection];
      jest.spyOn(unitsService, 'addUnitsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notifications });
      comp.ngOnInit();

      expect(unitsService.query).toHaveBeenCalled();
      expect(unitsService.addUnitsToCollectionIfMissing).toHaveBeenCalledWith(
        unitsCollection,
        ...additionalUnits.map(expect.objectContaining)
      );
      expect(comp.unitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const notifications: INotifications = { id: 456 };
      const unit: IUnits = { id: 75986 };
      notifications.unit = unit;

      activatedRoute.data = of({ notifications });
      comp.ngOnInit();

      expect(comp.unitsSharedCollection).toContain(unit);
      expect(comp.notifications).toEqual(notifications);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotifications>>();
      const notifications = { id: 123 };
      jest.spyOn(notificationsFormService, 'getNotifications').mockReturnValue(notifications);
      jest.spyOn(notificationsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notifications });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notifications }));
      saveSubject.complete();

      // THEN
      expect(notificationsFormService.getNotifications).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(notificationsService.update).toHaveBeenCalledWith(expect.objectContaining(notifications));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotifications>>();
      const notifications = { id: 123 };
      jest.spyOn(notificationsFormService, 'getNotifications').mockReturnValue({ id: null });
      jest.spyOn(notificationsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notifications: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notifications }));
      saveSubject.complete();

      // THEN
      expect(notificationsFormService.getNotifications).toHaveBeenCalled();
      expect(notificationsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotifications>>();
      const notifications = { id: 123 };
      jest.spyOn(notificationsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notifications });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(notificationsService.update).toHaveBeenCalled();
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
