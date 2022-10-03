import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditEducationGoalComponent } from './create-edit-education-goal.component';

describe('CreateEditEducationGoalComponent', () => {
  let component: CreateEditEducationGoalComponent;
  let fixture: ComponentFixture<CreateEditEducationGoalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditEducationGoalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditEducationGoalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
