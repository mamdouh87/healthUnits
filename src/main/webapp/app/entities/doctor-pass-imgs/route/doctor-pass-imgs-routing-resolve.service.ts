import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDoctorPassImgs } from '../doctor-pass-imgs.model';
import { DoctorPassImgsService } from '../service/doctor-pass-imgs.service';

@Injectable({ providedIn: 'root' })
export class DoctorPassImgsRoutingResolveService implements Resolve<IDoctorPassImgs | null> {
  constructor(protected service: DoctorPassImgsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoctorPassImgs | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((doctorPassImgs: HttpResponse<IDoctorPassImgs>) => {
          if (doctorPassImgs.body) {
            return of(doctorPassImgs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
