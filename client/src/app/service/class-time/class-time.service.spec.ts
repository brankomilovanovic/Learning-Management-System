import { TestBed } from '@angular/core/testing';

import { ClassTimeService } from './class-time.service';

describe('ClassTimeService', () => {
  let service: ClassTimeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClassTimeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
