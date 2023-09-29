import dayjs from 'dayjs/esm';

import { IExtraPassUnit, NewExtraPassUnit } from './extra-pass-unit.model';

export const sampleWithRequiredData: IExtraPassUnit = {
  id: 22085,
};

export const sampleWithPartialData: IExtraPassUnit = {
  id: 57551,
  activeWeek: 80654,
};

export const sampleWithFullData: IExtraPassUnit = {
  id: 89781,
  dayValue: 27615,
  shiftType: 96459,
  activeWeek: 63967,
  donePass: 96445,
  passDate: dayjs('2023-09-28T09:16'),
  unitPassed: 26900,
};

export const sampleWithNewData: NewExtraPassUnit = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
