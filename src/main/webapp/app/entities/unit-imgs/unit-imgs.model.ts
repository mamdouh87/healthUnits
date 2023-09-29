import { IUnits } from 'app/entities/units/units.model';

export interface IUnitImgs {
  id: number;
  fileDescription?: string | null;
  img?: string | null;
  imgContentType?: string | null;
  unit?: Pick<IUnits, 'id'> | null;
}

export type NewUnitImgs = Omit<IUnitImgs, 'id'> & { id: null };
