import { TestBed } from '@angular/core/testing';

import { TeacherOnRealizationService } from './teacher-on-realization.service';

describe('TeacherOnRealizationService', () => {
  let service: TeacherOnRealizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeacherOnRealizationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
