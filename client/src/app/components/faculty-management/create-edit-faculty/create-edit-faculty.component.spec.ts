import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditFacultyComponent } from './create-edit-faculty.component';

describe('CreateEditFacultyComponent', () => {
  let component: CreateEditFacultyComponent;
  let fixture: ComponentFixture<CreateEditFacultyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditFacultyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditFacultyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
