import { TestBed } from '@angular/core/testing';

import { SubjectNotificationsService } from './subject-notifications.service';

describe('SubjectNotificationsService', () => {
  let service: SubjectNotificationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectNotificationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
