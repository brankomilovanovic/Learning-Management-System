import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EducationGoalManagementComponent } from './education-goal-management.component';

describe('EducationGoalManagementComponent', () => {
  let component: EducationGoalManagementComponent;
  let fixture: ComponentFixture<EducationGoalManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EducationGoalManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EducationGoalManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
