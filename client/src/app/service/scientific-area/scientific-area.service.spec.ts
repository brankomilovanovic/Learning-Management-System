import { TestBed } from '@angular/core/testing';

import { ScientificAreaService } from './scientific-area.service';

describe('ScientificAreaService', () => {
  let service: ScientificAreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScientificAreaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
