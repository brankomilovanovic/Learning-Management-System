import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditStudentServiceComponent } from './create-edit-student-service.component';

describe('CreateEditStudentServiceComponent', () => {
  let component: CreateEditStudentServiceComponent;
  let fixture: ComponentFixture<CreateEditStudentServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditStudentServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditStudentServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
