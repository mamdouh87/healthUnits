import { IUnits } from 'app/entities/units/units.model';
import { IDoctors } from 'app/entities/doctors/doctors.model';

export interface IDoctorPassImgs {
  id: number;
  dayValue?: number | null;
  img?: string | null;
  imgContentType?: string | null;
  unit?: Pick<IUnits, 'id'> | null;
  doctor?: Pick<IDoctors, 'id'> | null;
}

export type NewDoctorPassImgs = Omit<IDoctorPassImgs, 'id'> & { id: null };
