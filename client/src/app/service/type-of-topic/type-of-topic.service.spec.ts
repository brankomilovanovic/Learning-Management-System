import { TestBed } from '@angular/core/testing';

import { TypeOfTopicService } from './type-of-topic.service';

describe('TypeOfTopicService', () => {
  let service: TypeOfTopicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeOfTopicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
