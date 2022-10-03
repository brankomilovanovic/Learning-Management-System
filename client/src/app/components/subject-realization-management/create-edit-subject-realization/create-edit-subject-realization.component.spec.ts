import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditSubjectRealizationComponent } from './create-edit-subject-realization.component';

describe('CreateEditSubjectRealizationComponent', () => {
  let component: CreateEditSubjectRealizationComponent;
  let fixture: ComponentFixture<CreateEditSubjectRealizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditSubjectRealizationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditSubjectRealizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
