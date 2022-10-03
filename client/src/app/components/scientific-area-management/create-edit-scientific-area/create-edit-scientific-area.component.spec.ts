import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditScientificAreaComponent } from './create-edit-scientific-area.component';

describe('CreateEditScientificAreaComponent', () => {
  let component: CreateEditScientificAreaComponent;
  let fixture: ComponentFixture<CreateEditScientificAreaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditScientificAreaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditScientificAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
