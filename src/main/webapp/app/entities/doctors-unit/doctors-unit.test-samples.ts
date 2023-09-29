import dayjs from 'dayjs/esm';

import { IDoctorsUnit, NewDoctorsUnit } from './doctors-unit.model';

export const sampleWithRequiredData: IDoctorsUnit = {
  id: 54462,
};

export const sampleWithPartialData: IDoctorsUnit = {
  id: 30889,
  activeWeek: 95418,
  passDate: dayjs('2023-09-28T02:57'),
  unitPassed: 88351,
  passBlocked: 72004,
};

export const sampleWithFullData: IDoctorsUnit = {
  id: 25191,
  dayValue: 2667,
  shiftType: 75669,
  activeWeek: 25129,
  donePass: 17013,
  passDate: dayjs('2023-09-28T12:45'),
  unitPassed: 22356,
  passBlocked: 33579,
  justView: 75760,
};

export const sampleWithNewData: NewDoctorsUnit = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
