import { TestBed } from '@angular/core/testing';

import { TypeEvaluationService } from './type-evaluation.service';

describe('TypeEvaluationService', () => {
  let service: TypeEvaluationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeEvaluationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
