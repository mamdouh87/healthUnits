import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWeekDayDate, NewWeekDayDate } from '../week-day-date.model';

export type PartialUpdateWeekDayDate = Partial<IWeekDayDate> & Pick<IWeekDayDate, 'id'>;

export type EntityResponseType = HttpResponse<IWeekDayDate>;
export type EntityArrayResponseType = HttpResponse<IWeekDayDate[]>;

@Injectable({ providedIn: 'root' })
export class WeekDayDateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/week-day-dates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(weekDayDate: NewWeekDayDate): Observable<EntityResponseType> {
    return this.http.post<IWeekDayDate>(this.resourceUrl, weekDayDate, { observe: 'response' });
  }

  update(weekDayDate: IWeekDayDate): Observable<EntityResponseType> {
    return this.http.put<IWeekDayDate>(`${this.resourceUrl}/${this.getWeekDayDateIdentifier(weekDayDate)}`, weekDayDate, {
      observe: 'response',
    });
  }

  partialUpdate(weekDayDate: PartialUpdateWeekDayDate): Observable<EntityResponseType> {
    return this.http.patch<IWeekDayDate>(`${this.resourceUrl}/${this.getWeekDayDateIdentifier(weekDayDate)}`, weekDayDate, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWeekDayDate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWeekDayDate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWeekDayDateIdentifier(weekDayDate: Pick<IWeekDayDate, 'id'>): number {
    return weekDayDate.id;
  }

  compareWeekDayDate(o1: Pick<IWeekDayDate, 'id'> | null, o2: Pick<IWeekDayDate, 'id'> | null): boolean {
    return o1 && o2 ? this.getWeekDayDateIdentifier(o1) === this.getWeekDayDateIdentifier(o2) : o1 === o2;
  }

  addWeekDayDateToCollectionIfMissing<Type extends Pick<IWeekDayDate, 'id'>>(
    weekDayDateCollection: Type[],
    ...weekDayDatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const weekDayDates: Type[] = weekDayDatesToCheck.filter(isPresent);
    if (weekDayDates.length > 0) {
      const weekDayDateCollectionIdentifiers = weekDayDateCollection.map(
        weekDayDateItem => this.getWeekDayDateIdentifier(weekDayDateItem)!
      );
      const weekDayDatesToAdd = weekDayDates.filter(weekDayDateItem => {
        const weekDayDateIdentifier = this.getWeekDayDateIdentifier(weekDayDateItem);
        if (weekDayDateCollectionIdentifiers.includes(weekDayDateIdentifier)) {
          return false;
        }
        weekDayDateCollectionIdentifiers.push(weekDayDateIdentifier);
        return true;
      });
      return [...weekDayDatesToAdd, ...weekDayDateCollection];
    }
    return weekDayDateCollection;
  }
}
