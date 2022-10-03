import { TestBed } from '@angular/core/testing';

import { TakingExamService } from './taking-exam.service';

describe('TakingExamService', () => {
  let service: TakingExamService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TakingExamService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
