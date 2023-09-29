import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExtraPassUnit } from '../extra-pass-unit.model';
import { ExtraPassUnitService } from '../service/extra-pass-unit.service';

@Injectable({ providedIn: 'root' })
export class ExtraPassUnitRoutingResolveService implements Resolve<IExtraPassUnit | null> {
  constructor(protected service: ExtraPassUnitService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExtraPassUnit | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((extraPassUnit: HttpResponse<IExtraPassUnit>) => {
          if (extraPassUnit.body) {
            return of(extraPassUnit.body);
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
