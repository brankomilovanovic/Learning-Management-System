import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditStudentOnTheYearComponent } from './create-edit-student-on-the-year.component';

describe('CreateEditStudentOnTheYearComponent', () => {
  let component: CreateEditStudentOnTheYearComponent;
  let fixture: ComponentFixture<CreateEditStudentOnTheYearComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditStudentOnTheYearComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditStudentOnTheYearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
