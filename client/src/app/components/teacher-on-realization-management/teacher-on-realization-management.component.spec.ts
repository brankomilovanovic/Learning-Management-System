import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherOnRealizationManagementComponent } from './teacher-on-realization-management.component';

describe('TeacherOnRealizationManagementComponent', () => {
  let component: TeacherOnRealizationManagementComponent;
  let fixture: ComponentFixture<TeacherOnRealizationManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeacherOnRealizationManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeacherOnRealizationManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
