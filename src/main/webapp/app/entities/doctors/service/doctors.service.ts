import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDoctors, NewDoctors } from '../doctors.model';

export type PartialUpdateDoctors = Partial<IDoctors> & Pick<IDoctors, 'id'>;

export type EntityResponseType = HttpResponse<IDoctors>;
export type EntityArrayResponseType = HttpResponse<IDoctors[]>;

@Injectable({ providedIn: 'root' })
export class DoctorsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/doctors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(doctors: NewDoctors): Observable<EntityResponseType> {
    return this.http.post<IDoctors>(this.resourceUrl, doctors, { observe: 'response' });
  }

  update(doctors: IDoctors): Observable<EntityResponseType> {
    return this.http.put<IDoctors>(`${this.resourceUrl}/${this.getDoctorsIdentifier(doctors)}`, doctors, { observe: 'response' });
  }

  partialUpdate(doctors: PartialUpdateDoctors): Observable<EntityResponseType> {
    return this.http.patch<IDoctors>(`${this.resourceUrl}/${this.getDoctorsIdentifier(doctors)}`, doctors, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoctors>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoctors[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDoctorsIdentifier(doctors: Pick<IDoctors, 'id'>): number {
    return doctors.id;
  }

  compareDoctors(o1: Pick<IDoctors, 'id'> | null, o2: Pick<IDoctors, 'id'> | null): boolean {
    return o1 && o2 ? this.getDoctorsIdentifier(o1) === this.getDoctorsIdentifier(o2) : o1 === o2;
  }

  addDoctorsToCollectionIfMissing<Type extends Pick<IDoctors, 'id'>>(
    doctorsCollection: Type[],
    ...doctorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const doctors: Type[] = doctorsToCheck.filter(isPresent);
    if (doctors.length > 0) {
      const doctorsCollectionIdentifiers = doctorsCollection.map(doctorsItem => this.getDoctorsIdentifier(doctorsItem)!);
      const doctorsToAdd = doctors.filter(doctorsItem => {
        const doctorsIdentifier = this.getDoctorsIdentifier(doctorsItem);
        if (doctorsCollectionIdentifiers.includes(doctorsIdentifier)) {
          return false;
        }
        doctorsCollectionIdentifiers.push(doctorsIdentifier);
        return true;
      });
      return [...doctorsToAdd, ...doctorsCollection];
    }
    return doctorsCollection;
  }
}
