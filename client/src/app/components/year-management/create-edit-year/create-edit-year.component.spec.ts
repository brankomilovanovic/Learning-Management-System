import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditYearComponent } from './create-edit-year.component';

describe('CreateEditYearComponent', () => {
  let component: CreateEditYearComponent;
  let fixture: ComponentFixture<CreateEditYearComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditYearComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditYearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
