import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentOnTheYearManagementComponent } from './student-on-the-year-management.component';

describe('StudentOnTheYearManagementComponent', () => {
  let component: StudentOnTheYearManagementComponent;
  let fixture: ComponentFixture<StudentOnTheYearManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudentOnTheYearManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentOnTheYearManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
