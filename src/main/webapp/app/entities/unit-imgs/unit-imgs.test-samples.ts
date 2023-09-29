import { IUnitImgs, NewUnitImgs } from './unit-imgs.model';

export const sampleWithRequiredData: IUnitImgs = {
  id: 98721,
};

export const sampleWithPartialData: IUnitImgs = {
  id: 3730,
};

export const sampleWithFullData: IUnitImgs = {
  id: 14396,
  fileDescription: 'Regional Fresh synthesizing',
  img: '../fake-data/blob/hipster.png',
  imgContentType: 'unknown',
};

export const sampleWithNewData: NewUnitImgs = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
