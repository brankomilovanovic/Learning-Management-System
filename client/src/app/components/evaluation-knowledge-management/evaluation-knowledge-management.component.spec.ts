import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationKnowledgeManagementComponent } from './evaluation-knowledge-management.component';

describe('EvaluationKnowledgeManagementComponent', () => {
  let component: EvaluationKnowledgeManagementComponent;
  let fixture: ComponentFixture<EvaluationKnowledgeManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvaluationKnowledgeManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluationKnowledgeManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
