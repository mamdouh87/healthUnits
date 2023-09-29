import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoctors } from '../doctors.model';

@Component({
  selector: 'jhi-doctors-detail',
  templateUrl: './doctors-detail.component.html',
})
export class DoctorsDetailComponent implements OnInit {
  doctors: IDoctors | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctors }) => {
      this.doctors = doctors;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
