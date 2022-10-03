import { TestBed } from '@angular/core/testing';

import { StudentOnTheYearService } from './student-on-the-year.service';

describe('StudentOnTheYearService', () => {
  let service: StudentOnTheYearService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentOnTheYearService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
