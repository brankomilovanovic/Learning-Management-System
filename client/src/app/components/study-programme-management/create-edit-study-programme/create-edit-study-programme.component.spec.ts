import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditStudyProgrammeComponent } from './create-edit-study-programme.component';

describe('CreateEditStudyProgrammeComponent', () => {
  let component: CreateEditStudyProgrammeComponent;
  let fixture: ComponentFixture<CreateEditStudyProgrammeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditStudyProgrammeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditStudyProgrammeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
