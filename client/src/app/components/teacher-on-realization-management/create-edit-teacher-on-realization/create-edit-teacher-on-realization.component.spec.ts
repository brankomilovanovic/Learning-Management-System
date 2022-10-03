import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditTeacherOnRealizationComponent } from './create-edit-teacher-on-realization.component';

describe('CreateEditTeacherOnRealizationComponent', () => {
  let component: CreateEditTeacherOnRealizationComponent;
  let fixture: ComponentFixture<CreateEditTeacherOnRealizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditTeacherOnRealizationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditTeacherOnRealizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
