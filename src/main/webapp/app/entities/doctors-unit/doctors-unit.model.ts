import dayjs from 'dayjs/esm';

export interface IDoctorsUnit {
  id: number;
  dayValue?: number | null;
  shiftType?: number | null;
  activeWeek?: number | null;
  donePass?: number | null;
  passDate?: dayjs.Dayjs | null;
  unitPassed?: number | null;
  passBlocked?: number | null;
  justView?: number | null;
}

export type NewDoctorsUnit = Omit<IDoctorsUnit, 'id'> & { id: null };
