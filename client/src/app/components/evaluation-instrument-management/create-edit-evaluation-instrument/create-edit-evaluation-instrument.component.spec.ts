import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditEvaluationInstrumentComponent } from './create-edit-evaluation-instrument.component';

describe('CreateEditEvaluationInstrumentComponent', () => {
  let component: CreateEditEvaluationInstrumentComponent;
  let fixture: ComponentFixture<CreateEditEvaluationInstrumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditEvaluationInstrumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditEvaluationInstrumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
