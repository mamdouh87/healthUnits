import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExtraPassUnit, NewExtraPassUnit } from '../extra-pass-unit.model';

export type PartialUpdateExtraPassUnit = Partial<IExtraPassUnit> & Pick<IExtraPassUnit, 'id'>;

type RestOf<T extends IExtraPassUnit | NewExtraPassUnit> = Omit<T, 'passDate'> & {
  passDate?: string | null;
};

export type RestExtraPassUnit = RestOf<IExtraPassUnit>;

export type NewRestExtraPassUnit = RestOf<NewExtraPassUnit>;

export type PartialUpdateRestExtraPassUnit = RestOf<PartialUpdateExtraPassUnit>;

export type EntityResponseType = HttpResponse<IExtraPassUnit>;
export type EntityArrayResponseType = HttpResponse<IExtraPassUnit[]>;

@Injectable({ providedIn: 'root' })
export class ExtraPassUnitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/extra-pass-units');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(extraPassUnit: NewExtraPassUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extraPassUnit);
    return this.http
      .post<RestExtraPassUnit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(extraPassUnit: IExtraPassUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extraPassUnit);
    return this.http
      .put<RestExtraPassUnit>(`${this.resourceUrl}/${this.getExtraPassUnitIdentifier(extraPassUnit)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(extraPassUnit: PartialUpdateExtraPassUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extraPassUnit);
    return this.http
      .patch<RestExtraPassUnit>(`${this.resourceUrl}/${this.getExtraPassUnitIdentifier(extraPassUnit)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestExtraPassUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestExtraPassUnit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getExtraPassUnitIdentifier(extraPassUnit: Pick<IExtraPassUnit, 'id'>): number {
    return extraPassUnit.id;
  }

  compareExtraPassUnit(o1: Pick<IExtraPassUnit, 'id'> | null, o2: Pick<IExtraPassUnit, 'id'> | null): boolean {
    return o1 && o2 ? this.getExtraPassUnitIdentifier(o1) === this.getExtraPassUnitIdentifier(o2) : o1 === o2;
  }

  addExtraPassUnitToCollectionIfMissing<Type extends Pick<IExtraPassUnit, 'id'>>(
    extraPassUnitCollection: Type[],
    ...extraPassUnitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const extraPassUnits: Type[] = extraPassUnitsToCheck.filter(isPresent);
    if (extraPassUnits.length > 0) {
      const extraPassUnitCollectionIdentifiers = extraPassUnitCollection.map(
        extraPassUnitItem => this.getExtraPassUnitIdentifier(extraPassUnitItem)!
      );
      const extraPassUnitsToAdd = extraPassUnits.filter(extraPassUnitItem => {
        const extraPassUnitIdentifier = this.getExtraPassUnitIdentifier(extraPassUnitItem);
        if (extraPassUnitCollectionIdentifiers.includes(extraPassUnitIdentifier)) {
          return false;
        }
        extraPassUnitCollectionIdentifiers.push(extraPassUnitIdentifier);
        return true;
      });
      return [...extraPassUnitsToAdd, ...extraPassUnitCollection];
    }
    return extraPassUnitCollection;
  }

  protected convertDateFromClient<T extends IExtraPassUnit | NewExtraPassUnit | PartialUpdateExtraPassUnit>(extraPassUnit: T): RestOf<T> {
    return {
      ...extraPassUnit,
      passDate: extraPassUnit.passDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restExtraPassUnit: RestExtraPassUnit): IExtraPassUnit {
    return {
      ...restExtraPassUnit,
      passDate: restExtraPassUnit.passDate ? dayjs(restExtraPassUnit.passDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestExtraPassUnit>): HttpResponse<IExtraPassUnit> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestExtraPassUnit[]>): HttpResponse<IExtraPassUnit[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
