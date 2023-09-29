import { IDoctorsUnit } from 'app/entities/doctors-unit/doctors-unit.model';
import { IExtraPassUnit } from 'app/entities/extra-pass-unit/extra-pass-unit.model';

export interface IUnits {
  id: number;
  name?: string | null;
  priority?: number | null;
  doctorsUnit?: Pick<IDoctorsUnit, 'id'> | null;
  extraPassUnit?: Pick<IExtraPassUnit, 'id'> | null;
}

export type NewUnits = Omit<IUnits, 'id'> & { id: null };
