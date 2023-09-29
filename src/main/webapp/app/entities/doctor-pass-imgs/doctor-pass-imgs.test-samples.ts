import { IDoctorPassImgs, NewDoctorPassImgs } from './doctor-pass-imgs.model';

export const sampleWithRequiredData: IDoctorPassImgs = {
  id: 24319,
};

export const sampleWithPartialData: IDoctorPassImgs = {
  id: 23544,
  img: '../fake-data/blob/hipster.png',
  imgContentType: 'unknown',
};

export const sampleWithFullData: IDoctorPassImgs = {
  id: 8749,
  dayValue: 33448,
  img: '../fake-data/blob/hipster.png',
  imgContentType: 'unknown',
};

export const sampleWithNewData: NewDoctorPassImgs = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
