import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditSubjectComponent } from './create-edit-subject.component';

describe('CreateEditSubjectComponent', () => {
  let component: CreateEditSubjectComponent;
  let fixture: ComponentFixture<CreateEditSubjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditSubjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
