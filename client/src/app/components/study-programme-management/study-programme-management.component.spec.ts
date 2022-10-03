import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudyProgrammeManagementComponent } from './study-programme-management.component';

describe('StudyProgrammeManagementComponent', () => {
  let component: StudyProgrammeManagementComponent;
  let fixture: ComponentFixture<StudyProgrammeManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudyProgrammeManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudyProgrammeManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
