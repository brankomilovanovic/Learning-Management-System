import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditEvaluationKnowledgeComponent } from './create-edit-evaluation-knowledge.component';

describe('CreateEditEvaluationKnowledgeComponent', () => {
  let component: CreateEditEvaluationKnowledgeComponent;
  let fixture: ComponentFixture<CreateEditEvaluationKnowledgeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditEvaluationKnowledgeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditEvaluationKnowledgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
