import { INotifications, NewNotifications } from './notifications.model';

export const sampleWithRequiredData: INotifications = {
  id: 98929,
};

export const sampleWithPartialData: INotifications = {
  id: 75729,
  title: 'Avon',
  status: 73813,
};

export const sampleWithFullData: INotifications = {
  id: 97309,
  title: 'Outdoors',
  message: 'Dam Island National',
  status: 19262,
  dayValue: 55320,
};

export const sampleWithNewData: NewNotifications = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
