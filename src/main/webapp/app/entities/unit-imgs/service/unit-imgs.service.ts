import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUnitImgs, NewUnitImgs } from '../unit-imgs.model';

export type PartialUpdateUnitImgs = Partial<IUnitImgs> & Pick<IUnitImgs, 'id'>;

export type EntityResponseType = HttpResponse<IUnitImgs>;
export type EntityArrayResponseType = HttpResponse<IUnitImgs[]>;

@Injectable({ providedIn: 'root' })
export class UnitImgsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/unit-imgs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(unitImgs: NewUnitImgs): Observable<EntityResponseType> {
    return this.http.post<IUnitImgs>(this.resourceUrl, unitImgs, { observe: 'response' });
  }

  update(unitImgs: IUnitImgs): Observable<EntityResponseType> {
    return this.http.put<IUnitImgs>(`${this.resourceUrl}/${this.getUnitImgsIdentifier(unitImgs)}`, unitImgs, { observe: 'response' });
  }

  partialUpdate(unitImgs: PartialUpdateUnitImgs): Observable<EntityResponseType> {
    return this.http.patch<IUnitImgs>(`${this.resourceUrl}/${this.getUnitImgsIdentifier(unitImgs)}`, unitImgs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnitImgs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnitImgs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUnitImgsIdentifier(unitImgs: Pick<IUnitImgs, 'id'>): number {
    return unitImgs.id;
  }

  compareUnitImgs(o1: Pick<IUnitImgs, 'id'> | null, o2: Pick<IUnitImgs, 'id'> | null): boolean {
    return o1 && o2 ? this.getUnitImgsIdentifier(o1) === this.getUnitImgsIdentifier(o2) : o1 === o2;
  }

  addUnitImgsToCollectionIfMissing<Type extends Pick<IUnitImgs, 'id'>>(
    unitImgsCollection: Type[],
    ...unitImgsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const unitImgs: Type[] = unitImgsToCheck.filter(isPresent);
    if (unitImgs.length > 0) {
      const unitImgsCollectionIdentifiers = unitImgsCollection.map(unitImgsItem => this.getUnitImgsIdentifier(unitImgsItem)!);
      const unitImgsToAdd = unitImgs.filter(unitImgsItem => {
        const unitImgsIdentifier = this.getUnitImgsIdentifier(unitImgsItem);
        if (unitImgsCollectionIdentifiers.includes(unitImgsIdentifier)) {
          return false;
        }
        unitImgsCollectionIdentifiers.push(unitImgsIdentifier);
        return true;
      });
      return [...unitImgsToAdd, ...unitImgsCollection];
    }
    return unitImgsCollection;
  }
}
