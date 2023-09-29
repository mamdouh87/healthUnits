import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INotifications, NewNotifications } from '../notifications.model';

export type PartialUpdateNotifications = Partial<INotifications> & Pick<INotifications, 'id'>;

export type EntityResponseType = HttpResponse<INotifications>;
export type EntityArrayResponseType = HttpResponse<INotifications[]>;

@Injectable({ providedIn: 'root' })
export class NotificationsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/notifications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(notifications: NewNotifications): Observable<EntityResponseType> {
    return this.http.post<INotifications>(this.resourceUrl, notifications, { observe: 'response' });
  }

  update(notifications: INotifications): Observable<EntityResponseType> {
    return this.http.put<INotifications>(`${this.resourceUrl}/${this.getNotificationsIdentifier(notifications)}`, notifications, {
      observe: 'response',
    });
  }

  partialUpdate(notifications: PartialUpdateNotifications): Observable<EntityResponseType> {
    return this.http.patch<INotifications>(`${this.resourceUrl}/${this.getNotificationsIdentifier(notifications)}`, notifications, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INotifications>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INotifications[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNotificationsIdentifier(notifications: Pick<INotifications, 'id'>): number {
    return notifications.id;
  }

  compareNotifications(o1: Pick<INotifications, 'id'> | null, o2: Pick<INotifications, 'id'> | null): boolean {
    return o1 && o2 ? this.getNotificationsIdentifier(o1) === this.getNotificationsIdentifier(o2) : o1 === o2;
  }

  addNotificationsToCollectionIfMissing<Type extends Pick<INotifications, 'id'>>(
    notificationsCollection: Type[],
    ...notificationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notifications: Type[] = notificationsToCheck.filter(isPresent);
    if (notifications.length > 0) {
      const notificationsCollectionIdentifiers = notificationsCollection.map(
        notificationsItem => this.getNotificationsIdentifier(notificationsItem)!
      );
      const notificationsToAdd = notifications.filter(notificationsItem => {
        const notificationsIdentifier = this.getNotificationsIdentifier(notificationsItem);
        if (notificationsCollectionIdentifiers.includes(notificationsIdentifier)) {
          return false;
        }
        notificationsCollectionIdentifiers.push(notificationsIdentifier);
        return true;
      });
      return [...notificationsToAdd, ...notificationsCollection];
    }
    return notificationsCollection;
  }
}
