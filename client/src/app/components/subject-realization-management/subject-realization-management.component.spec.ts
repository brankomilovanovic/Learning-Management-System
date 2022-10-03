import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectRealizationManagementComponent } from './subject-realization-management.component';

describe('SubjectRealizationManagementComponent', () => {
  let component: SubjectRealizationManagementComponent;
  let fixture: ComponentFixture<SubjectRealizationManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubjectRealizationManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubjectRealizationManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
