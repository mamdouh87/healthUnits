import dayjs from 'dayjs/esm';

export interface IExtraPassUnit {
  id: number;
  dayValue?: number | null;
  shiftType?: number | null;
  activeWeek?: number | null;
  donePass?: number | null;
  passDate?: dayjs.Dayjs | null;
  unitPassed?: number | null;
}

export type NewExtraPassUnit = Omit<IExtraPassUnit, 'id'> & { id: null };
