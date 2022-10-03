import { TestBed } from '@angular/core/testing';

import { SubjectRealizationService } from './subject-realization.service';

describe('SubjectRealizationService', () => {
  let service: SubjectRealizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectRealizationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
