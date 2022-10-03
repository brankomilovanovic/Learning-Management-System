import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationInstrumentComponent } from './evaluation-instrument.component';

describe('EvaluationInstrumentComponent', () => {
  let component: EvaluationInstrumentComponent;
  let fixture: ComponentFixture<EvaluationInstrumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvaluationInstrumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluationInstrumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
