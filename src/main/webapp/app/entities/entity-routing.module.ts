import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'units',
        data: { pageTitle: 'healthUnitApp.units.home.title' },
        loadChildren: () => import('./units/units.module').then(m => m.UnitsModule),
      },
      {
        path: 'doctors',
        data: { pageTitle: 'healthUnitApp.doctors.home.title' },
        loadChildren: () => import('./doctors/doctors.module').then(m => m.DoctorsModule),
      },
      {
        path: 'doctors-unit',
        data: { pageTitle: 'healthUnitApp.doctorsUnit.home.title' },
        loadChildren: () => import('./doctors-unit/doctors-unit.module').then(m => m.DoctorsUnitModule),
      },
      {
        path: 'extra-pass-unit',
        data: { pageTitle: 'healthUnitApp.extraPassUnit.home.title' },
        loadChildren: () => import('./extra-pass-unit/extra-pass-unit.module').then(m => m.ExtraPassUnitModule),
      },
      {
        path: 'unit-imgs',
        data: { pageTitle: 'healthUnitApp.unitImgs.home.title' },
        loadChildren: () => import('./unit-imgs/unit-imgs.module').then(m => m.UnitImgsModule),
      },
      {
        path: 'week-day-date',
        data: { pageTitle: 'healthUnitApp.weekDayDate.home.title' },
        loadChildren: () => import('./week-day-date/week-day-date.module').then(m => m.WeekDayDateModule),
      },
      {
        path: 'doctor-pass-imgs',
        data: { pageTitle: 'healthUnitApp.doctorPassImgs.home.title' },
        loadChildren: () => import('./doctor-pass-imgs/doctor-pass-imgs.module').then(m => m.DoctorPassImgsModule),
      },
      {
        path: 'notifications',
        data: { pageTitle: 'healthUnitApp.notifications.home.title' },
        loadChildren: () => import('./notifications/notifications.module').then(m => m.NotificationsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
