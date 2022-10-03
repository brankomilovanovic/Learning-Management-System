import { TestBed } from '@angular/core/testing';

import { KorisnikNaForumuService } from './korisnik-na-forumu.service';

describe('KorisnikNaForumuService', () => {
  let service: KorisnikNaForumuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KorisnikNaForumuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
