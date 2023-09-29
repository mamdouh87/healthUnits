import { IDoctors, NewDoctors } from './doctors.model';

export const sampleWithRequiredData: IDoctors = {
  id: 11682,
};

export const sampleWithPartialData: IDoctors = {
  id: 74322,
  doubleShift: 95978,
};

export const sampleWithFullData: IDoctors = {
  id: 96405,
  name: 'Bedfordshire Czech collaborative',
  perferedDay: 'Minor Grove',
  doubleShift: 62356,
  shiftType: 'pixel',
  perferedUnit: 'Web Producer Account',
};

export const sampleWithNewData: NewDoctors = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
