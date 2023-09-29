import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDoctorsUnit, NewDoctorsUnit } from '../doctors-unit.model';

export type PartialUpdateDoctorsUnit = Partial<IDoctorsUnit> & Pick<IDoctorsUnit, 'id'>;

type RestOf<T extends IDoctorsUnit | NewDoctorsUnit> = Omit<T, 'passDate'> & {
  passDate?: string | null;
};

export type RestDoctorsUnit = RestOf<IDoctorsUnit>;

export type NewRestDoctorsUnit = RestOf<NewDoctorsUnit>;

export type PartialUpdateRestDoctorsUnit = RestOf<PartialUpdateDoctorsUnit>;

export type EntityResponseType = HttpResponse<IDoctorsUnit>;
export type EntityArrayResponseType = HttpResponse<IDoctorsUnit[]>;

@Injectable({ providedIn: 'root' })
export class DoctorsUnitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/doctors-units');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(doctorsUnit: NewDoctorsUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctorsUnit);
    return this.http
      .post<RestDoctorsUnit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(doctorsUnit: IDoctorsUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctorsUnit);
    return this.http
      .put<RestDoctorsUnit>(`${this.resourceUrl}/${this.getDoctorsUnitIdentifier(doctorsUnit)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(doctorsUnit: PartialUpdateDoctorsUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctorsUnit);
    return this.http
      .patch<RestDoctorsUnit>(`${this.resourceUrl}/${this.getDoctorsUnitIdentifier(doctorsUnit)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDoctorsUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDoctorsUnit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDoctorsUnitIdentifier(doctorsUnit: Pick<IDoctorsUnit, 'id'>): number {
    return doctorsUnit.id;
  }

  compareDoctorsUnit(o1: Pick<IDoctorsUnit, 'id'> | null, o2: Pick<IDoctorsUnit, 'id'> | null): boolean {
    return o1 && o2 ? this.getDoctorsUnitIdentifier(o1) === this.getDoctorsUnitIdentifier(o2) : o1 === o2;
  }

  addDoctorsUnitToCollectionIfMissing<Type extends Pick<IDoctorsUnit, 'id'>>(
    doctorsUnitCollection: Type[],
    ...doctorsUnitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const doctorsUnits: Type[] = doctorsUnitsToCheck.filter(isPresent);
    if (doctorsUnits.length > 0) {
      const doctorsUnitCollectionIdentifiers = doctorsUnitCollection.map(
        doctorsUnitItem => this.getDoctorsUnitIdentifier(doctorsUnitItem)!
      );
      const doctorsUnitsToAdd = doctorsUnits.filter(doctorsUnitItem => {
        const doctorsUnitIdentifier = this.getDoctorsUnitIdentifier(doctorsUnitItem);
        if (doctorsUnitCollectionIdentifiers.includes(doctorsUnitIdentifier)) {
          return false;
        }
        doctorsUnitCollectionIdentifiers.push(doctorsUnitIdentifier);
        return true;
      });
      return [...doctorsUnitsToAdd, ...doctorsUnitCollection];
    }
    return doctorsUnitCollection;
  }

  protected convertDateFromClient<T extends IDoctorsUnit | NewDoctorsUnit | PartialUpdateDoctorsUnit>(doctorsUnit: T): RestOf<T> {
    return {
      ...doctorsUnit,
      passDate: doctorsUnit.passDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDoctorsUnit: RestDoctorsUnit): IDoctorsUnit {
    return {
      ...restDoctorsUnit,
      passDate: restDoctorsUnit.passDate ? dayjs(restDoctorsUnit.passDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDoctorsUnit>): HttpResponse<IDoctorsUnit> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDoctorsUnit[]>): HttpResponse<IDoctorsUnit[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
