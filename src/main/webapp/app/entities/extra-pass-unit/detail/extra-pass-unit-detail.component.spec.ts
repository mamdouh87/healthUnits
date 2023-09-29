import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExtraPassUnitDetailComponent } from './extra-pass-unit-detail.component';

describe('ExtraPassUnit Management Detail Component', () => {
  let comp: ExtraPassUnitDetailComponent;
  let fixture: ComponentFixture<ExtraPassUnitDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExtraPassUnitDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ extraPassUnit: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ExtraPassUnitDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExtraPassUnitDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load extraPassUnit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.extraPassUnit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
