import { TestBed } from '@angular/core/testing';

import { EvaluationInstrumentService } from './evaluation-instrument.service';

describe('EvaluationInstrumentService', () => {
  let service: EvaluationInstrumentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EvaluationInstrumentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
