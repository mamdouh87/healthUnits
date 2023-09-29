export interface IWeekDayDate {
  id: number;
  dayValue?: number | null;
  dayName?: string | null;
  dayDate?: string | null;
}

export type NewWeekDayDate = Omit<IWeekDayDate, 'id'> & { id: null };
