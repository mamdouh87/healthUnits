import { IWeekDayDate, NewWeekDayDate } from './week-day-date.model';

export const sampleWithRequiredData: IWeekDayDate = {
  id: 64708,
};

export const sampleWithPartialData: IWeekDayDate = {
  id: 3438,
  dayName: 'context-sensitive',
  dayDate: 'Officer FTP',
};

export const sampleWithFullData: IWeekDayDate = {
  id: 67439,
  dayValue: 32678,
  dayName: 'Dynamic Plains online',
  dayDate: 'parsing mindshare Avon',
};

export const sampleWithNewData: NewWeekDayDate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
