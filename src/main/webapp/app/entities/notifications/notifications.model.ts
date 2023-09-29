import { IUnits } from 'app/entities/units/units.model';

export interface INotifications {
  id: number;
  title?: string | null;
  message?: string | null;
  status?: number | null;
  dayValue?: number | null;
  unit?: Pick<IUnits, 'id'> | null;
}

export type NewNotifications = Omit<INotifications, 'id'> & { id: null };
