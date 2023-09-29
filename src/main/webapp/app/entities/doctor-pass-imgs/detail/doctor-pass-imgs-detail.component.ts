import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoctorPassImgs } from '../doctor-pass-imgs.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-doctor-pass-imgs-detail',
  templateUrl: './doctor-pass-imgs-detail.component.html',
})
export class DoctorPassImgsDetailComponent implements OnInit {
  doctorPassImgs: IDoctorPassImgs | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctorPassImgs }) => {
      this.doctorPassImgs = doctorPassImgs;
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
