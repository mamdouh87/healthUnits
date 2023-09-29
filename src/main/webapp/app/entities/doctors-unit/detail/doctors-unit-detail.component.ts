import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoctorsUnit } from '../doctors-unit.model';

@Component({
  selector: 'jhi-doctors-unit-detail',
  templateUrl: './doctors-unit-detail.component.html',
})
export class DoctorsUnitDetailComponent implements OnInit {
  doctorsUnit: IDoctorsUnit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctorsUnit }) => {
      this.doctorsUnit = doctorsUnit;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
