import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDoctorsUnit } from '../doctors-unit.model';
import { DoctorsUnitService } from '../service/doctors-unit.service';

@Injectable({ providedIn: 'root' })
export class DoctorsUnitRoutingResolveService implements Resolve<IDoctorsUnit | null> {
  constructor(protected service: DoctorsUnitService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoctorsUnit | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((doctorsUnit: HttpResponse<IDoctorsUnit>) => {
          if (doctorsUnit.body) {
            return of(doctorsUnit.body);
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
