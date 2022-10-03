import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateStudentComponent } from './rate-student.component';

describe('RateStudentComponent', () => {
  let component: RateStudentComponent;
  let fixture: ComponentFixture<RateStudentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateStudentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
