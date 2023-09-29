import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INotifications } from '../notifications.model';
import { NotificationsService } from '../service/notifications.service';

@Injectable({ providedIn: 'root' })
export class NotificationsRoutingResolveService implements Resolve<INotifications | null> {
  constructor(protected service: NotificationsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotifications | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((notifications: HttpResponse<INotifications>) => {
          if (notifications.body) {
            return of(notifications.body);
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
