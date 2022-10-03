import { TestBed } from '@angular/core/testing';

import { PodforumService } from './podforum.service';

describe('PodforumService', () => {
  let service: PodforumService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PodforumService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
