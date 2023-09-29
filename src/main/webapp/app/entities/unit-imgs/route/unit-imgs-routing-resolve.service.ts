import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUnitImgs } from '../unit-imgs.model';
import { UnitImgsService } from '../service/unit-imgs.service';

@Injectable({ providedIn: 'root' })
export class UnitImgsRoutingResolveService implements Resolve<IUnitImgs | null> {
  constructor(protected service: UnitImgsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnitImgs | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((unitImgs: HttpResponse<IUnitImgs>) => {
          if (unitImgs.body) {
            return of(unitImgs.body);
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
