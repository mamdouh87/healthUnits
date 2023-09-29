import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnitImgs } from '../unit-imgs.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-unit-imgs-detail',
  templateUrl: './unit-imgs-detail.component.html',
})
export class UnitImgsDetailComponent implements OnInit {
  unitImgs: IUnitImgs | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitImgs }) => {
      this.unitImgs = unitImgs;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
