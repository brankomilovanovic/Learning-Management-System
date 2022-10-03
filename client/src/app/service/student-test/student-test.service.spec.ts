import { TestBed } from '@angular/core/testing';

import { StudentTestService } from './student-test.service';

describe('StudentTestService', () => {
  let service: StudentTestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentTestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
