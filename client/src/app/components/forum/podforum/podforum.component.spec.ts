import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PodforumComponent } from './podforum.component';

describe('PodforumComponent', () => {
  let component: PodforumComponent;
  let fixture: ComponentFixture<PodforumComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PodforumComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PodforumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
