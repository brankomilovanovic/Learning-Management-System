import { TestBed } from '@angular/core/testing';

import { EvaluationKnowledgeService } from './evaluation-knowledge.service';

describe('EvaluationKnowledgeService', () => {
  let service: EvaluationKnowledgeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EvaluationKnowledgeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
