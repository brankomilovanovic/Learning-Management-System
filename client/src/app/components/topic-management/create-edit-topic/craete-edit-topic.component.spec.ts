import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CraeteEditTopicComponent } from './craete-edit-topic.component';

describe('CraeteEditTopicComponent', () => {
  let component: CraeteEditTopicComponent;
  let fixture: ComponentFixture<CraeteEditTopicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CraeteEditTopicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CraeteEditTopicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
