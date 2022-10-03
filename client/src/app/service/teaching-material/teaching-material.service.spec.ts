import { TestBed } from '@angular/core/testing';

import { TeachingMaterialService } from './teaching-material.service';

describe('TeachingMaterialService', () => {
  let service: TeachingMaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeachingMaterialService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
