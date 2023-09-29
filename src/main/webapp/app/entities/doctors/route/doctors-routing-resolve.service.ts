import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDoctors } from '../doctors.model';
import { DoctorsService } from '../service/doctors.service';

@Injectable({ providedIn: 'root' })
export class DoctorsRoutingResolveService implements Resolve<IDoctors | null> {
  constructor(protected service: DoctorsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoctors | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((doctors: HttpResponse<IDoctors>) => {
          if (doctors.body) {
            return of(doctors.body);
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
