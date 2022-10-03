import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditUniversityComponent } from './create-edit-university.component';

describe('CreateEditUniversityComponent', () => {
  let component: CreateEditUniversityComponent;
  let fixture: ComponentFixture<CreateEditUniversityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditUniversityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditUniversityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
