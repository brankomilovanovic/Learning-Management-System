import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentServiceManagementComponent } from './student-service-management.component';

describe('StudentServiceManagementComponent', () => {
  let component: StudentServiceManagementComponent;
  let fixture: ComponentFixture<StudentServiceManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudentServiceManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentServiceManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
