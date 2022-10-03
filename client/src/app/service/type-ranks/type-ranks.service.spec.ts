import { TestBed } from '@angular/core/testing';

import { TypeRanksService } from './type-ranks.service';

describe('TypeRanksService', () => {
  let service: TypeRanksService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeRanksService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
