import { IUnits, NewUnits } from './units.model';

export const sampleWithRequiredData: IUnits = {
  id: 89568,
};

export const sampleWithPartialData: IUnits = {
  id: 45071,
};

export const sampleWithFullData: IUnits = {
  id: 75980,
  name: 'Toys',
  priority: 17471,
};

export const sampleWithNewData: NewUnits = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
