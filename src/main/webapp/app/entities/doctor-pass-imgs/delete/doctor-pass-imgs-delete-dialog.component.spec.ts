jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DoctorPassImgsService } from '../service/doctor-pass-imgs.service';

import { DoctorPassImgsDeleteDialogComponent } from './doctor-pass-imgs-delete-dialog.component';

describe('DoctorPassImgs Management Delete Component', () => {
  let comp: DoctorPassImgsDeleteDialogComponent;
  let fixture: ComponentFixture<DoctorPassImgsDeleteDialogComponent>;
  let service: DoctorPassImgsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DoctorPassImgsDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(DoctorPassImgsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DoctorPassImgsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DoctorPassImgsService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
