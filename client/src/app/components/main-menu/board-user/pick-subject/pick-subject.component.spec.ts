import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PickSubjectComponent } from './pick-subject.component';

describe('PickSubjectComponent', () => {
  let component: PickSubjectComponent;
  let fixture: ComponentFixture<PickSubjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PickSubjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PickSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
