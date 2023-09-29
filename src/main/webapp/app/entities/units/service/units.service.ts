import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUnits, NewUnits } from '../units.model';

export type PartialUpdateUnits = Partial<IUnits> & Pick<IUnits, 'id'>;

export type EntityResponseType = HttpResponse<IUnits>;
export type EntityArrayResponseType = HttpResponse<IUnits[]>;

@Injectable({ providedIn: 'root' })
export class UnitsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/units');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(units: NewUnits): Observable<EntityResponseType> {
    return this.http.post<IUnits>(this.resourceUrl, units, { observe: 'response' });
  }

  update(units: IUnits): Observable<EntityResponseType> {
    return this.http.put<IUnits>(`${this.resourceUrl}/${this.getUnitsIdentifier(units)}`, units, { observe: 'response' });
  }

  partialUpdate(units: PartialUpdateUnits): Observable<EntityResponseType> {
    return this.http.patch<IUnits>(`${this.resourceUrl}/${this.getUnitsIdentifier(units)}`, units, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnits>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnits[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUnitsIdentifier(units: Pick<IUnits, 'id'>): number {
    return units.id;
  }

  compareUnits(o1: Pick<IUnits, 'id'> | null, o2: Pick<IUnits, 'id'> | null): boolean {
    return o1 && o2 ? this.getUnitsIdentifier(o1) === this.getUnitsIdentifier(o2) : o1 === o2;
  }

  addUnitsToCollectionIfMissing<Type extends Pick<IUnits, 'id'>>(
    unitsCollection: Type[],
    ...unitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const units: Type[] = unitsToCheck.filter(isPresent);
    if (units.length > 0) {
      const unitsCollectionIdentifiers = unitsCollection.map(unitsItem => this.getUnitsIdentifier(unitsItem)!);
      const unitsToAdd = units.filter(unitsItem => {
        const unitsIdentifier = this.getUnitsIdentifier(unitsItem);
        if (unitsCollectionIdentifiers.includes(unitsIdentifier)) {
          return false;
        }
        unitsCollectionIdentifiers.push(unitsIdentifier);
        return true;
      });
      return [...unitsToAdd, ...unitsCollection];
    }
    return unitsCollection;
  }
}
