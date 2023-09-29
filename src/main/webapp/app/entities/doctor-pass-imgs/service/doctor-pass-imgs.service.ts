import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDoctorPassImgs, NewDoctorPassImgs } from '../doctor-pass-imgs.model';

export type PartialUpdateDoctorPassImgs = Partial<IDoctorPassImgs> & Pick<IDoctorPassImgs, 'id'>;

export type EntityResponseType = HttpResponse<IDoctorPassImgs>;
export type EntityArrayResponseType = HttpResponse<IDoctorPassImgs[]>;

@Injectable({ providedIn: 'root' })
export class DoctorPassImgsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/doctor-pass-imgs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(doctorPassImgs: NewDoctorPassImgs): Observable<EntityResponseType> {
    return this.http.post<IDoctorPassImgs>(this.resourceUrl, doctorPassImgs, { observe: 'response' });
  }

  update(doctorPassImgs: IDoctorPassImgs): Observable<EntityResponseType> {
    return this.http.put<IDoctorPassImgs>(`${this.resourceUrl}/${this.getDoctorPassImgsIdentifier(doctorPassImgs)}`, doctorPassImgs, {
      observe: 'response',
    });
  }

  partialUpdate(doctorPassImgs: PartialUpdateDoctorPassImgs): Observable<EntityResponseType> {
    return this.http.patch<IDoctorPassImgs>(`${this.resourceUrl}/${this.getDoctorPassImgsIdentifier(doctorPassImgs)}`, doctorPassImgs, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoctorPassImgs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoctorPassImgs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDoctorPassImgsIdentifier(doctorPassImgs: Pick<IDoctorPassImgs, 'id'>): number {
    return doctorPassImgs.id;
  }

  compareDoctorPassImgs(o1: Pick<IDoctorPassImgs, 'id'> | null, o2: Pick<IDoctorPassImgs, 'id'> | null): boolean {
    return o1 && o2 ? this.getDoctorPassImgsIdentifier(o1) === this.getDoctorPassImgsIdentifier(o2) : o1 === o2;
  }

  addDoctorPassImgsToCollectionIfMissing<Type extends Pick<IDoctorPassImgs, 'id'>>(
    doctorPassImgsCollection: Type[],
    ...doctorPassImgsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const doctorPassImgs: Type[] = doctorPassImgsToCheck.filter(isPresent);
    if (doctorPassImgs.length > 0) {
      const doctorPassImgsCollectionIdentifiers = doctorPassImgsCollection.map(
        doctorPassImgsItem => this.getDoctorPassImgsIdentifier(doctorPassImgsItem)!
      );
      const doctorPassImgsToAdd = doctorPassImgs.filter(doctorPassImgsItem => {
        const doctorPassImgsIdentifier = this.getDoctorPassImgsIdentifier(doctorPassImgsItem);
        if (doctorPassImgsCollectionIdentifiers.includes(doctorPassImgsIdentifier)) {
          return false;
        }
        doctorPassImgsCollectionIdentifiers.push(doctorPassImgsIdentifier);
        return true;
      });
      return [...doctorPassImgsToAdd, ...doctorPassImgsCollection];
    }
    return doctorPassImgsCollection;
  }
}
