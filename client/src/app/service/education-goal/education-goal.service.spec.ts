import { TestBed } from '@angular/core/testing';

import { EducationGoalService } from './education-goal.service';

describe('EducationGoalService', () => {
  let service: EducationGoalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EducationGoalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
