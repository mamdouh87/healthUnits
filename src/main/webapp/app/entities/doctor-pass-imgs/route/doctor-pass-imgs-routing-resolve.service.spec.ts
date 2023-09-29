import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDoctorPassImgs } from '../doctor-pass-imgs.model';
import { DoctorPassImgsService } from '../service/doctor-pass-imgs.service';

import { DoctorPassImgsRoutingResolveService } from './doctor-pass-imgs-routing-resolve.service';

describe('DoctorPassImgs routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DoctorPassImgsRoutingResolveService;
  let service: DoctorPassImgsService;
  let resultDoctorPassImgs: IDoctorPassImgs | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(DoctorPassImgsRoutingResolveService);
    service = TestBed.inject(DoctorPassImgsService);
    resultDoctorPassImgs = undefined;
  });

  describe('resolve', () => {
    it('should return IDoctorPassImgs returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDoctorPassImgs = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDoctorPassImgs).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDoctorPassImgs = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDoctorPassImgs).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDoctorPassImgs>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDoctorPassImgs = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDoctorPassImgs).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
