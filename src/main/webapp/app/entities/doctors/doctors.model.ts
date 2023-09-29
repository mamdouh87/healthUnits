import { IDoctorsUnit } from 'app/entities/doctors-unit/doctors-unit.model';
import { IExtraPassUnit } from 'app/entities/extra-pass-unit/extra-pass-unit.model';

export interface IDoctors {
  id: number;
  name?: string | null;
  perferedDay?: string | null;
  doubleShift?: number | null;
  shiftType?: string | null;
  perferedUnit?: string | null;
  doctorsUnit?: Pick<IDoctorsUnit, 'id'> | null;
  extraPassUnit?: Pick<IExtraPassUnit, 'id'> | null;
}

export type NewDoctors = Omit<IDoctors, 'id'> & { id: null };
