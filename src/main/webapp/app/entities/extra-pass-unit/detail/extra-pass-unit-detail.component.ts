import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtraPassUnit } from '../extra-pass-unit.model';

@Component({
  selector: 'jhi-extra-pass-unit-detail',
  templateUrl: './extra-pass-unit-detail.component.html',
})
export class ExtraPassUnitDetailComponent implements OnInit {
  extraPassUnit: IExtraPassUnit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extraPassUnit }) => {
      this.extraPassUnit = extraPassUnit;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
