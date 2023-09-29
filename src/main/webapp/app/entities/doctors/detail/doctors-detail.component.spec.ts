import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DoctorsDetailComponent } from './doctors-detail.component';

describe('Doctors Management Detail Component', () => {
  let comp: DoctorsDetailComponent;
  let fixture: ComponentFixture<DoctorsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DoctorsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ doctors: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DoctorsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DoctorsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load doctors on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.doctors).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
