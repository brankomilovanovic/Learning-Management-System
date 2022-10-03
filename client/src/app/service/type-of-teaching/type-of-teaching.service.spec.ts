import { TestBed } from '@angular/core/testing';

import { TypeOfTeachingService } from './type-of-teaching.service';

describe('TypeOfTeachingService', () => {
  let service: TypeOfTeachingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeOfTeachingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
