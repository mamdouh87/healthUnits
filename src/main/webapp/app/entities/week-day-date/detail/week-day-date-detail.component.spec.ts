import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeekDayDateDetailComponent } from './week-day-date-detail.component';

describe('WeekDayDate Management Detail Component', () => {
  let comp: WeekDayDateDetailComponent;
  let fixture: ComponentFixture<WeekDayDateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WeekDayDateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ weekDayDate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(WeekDayDateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WeekDayDateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load weekDayDate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.weekDayDate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
