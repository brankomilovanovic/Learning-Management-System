import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientificAreaManagementComponent } from './scientific-area-management.component';

describe('ScientificAreaManagementComponent', () => {
  let component: ScientificAreaManagementComponent;
  let fixture: ComponentFixture<ScientificAreaManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientificAreaManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientificAreaManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
