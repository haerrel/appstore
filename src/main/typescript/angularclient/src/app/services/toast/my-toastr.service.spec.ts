import { TestBed } from '@angular/core/testing';

import { MyToastrService } from './my-toastr.service';

describe('MyToastrService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MyToastrService = TestBed.get(MyToastrService);
    expect(service).toBeTruthy();
  });
});
