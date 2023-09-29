import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DoctorsUnitDetailComponent } from './doctors-unit-detail.component';

describe('DoctorsUnit Management Detail Component', () => {
  let comp: DoctorsUnitDetailComponent;
  let fixture: ComponentFixture<DoctorsUnitDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DoctorsUnitDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ doctorsUnit: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DoctorsUnitDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DoctorsUnitDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load doctorsUnit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.doctorsUnit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
