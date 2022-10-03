import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditClassTimeComponent } from './create-edit-class-time.component';

describe('CreateEditClassTimeComponent', () => {
  let component: CreateEditClassTimeComponent;
  let fixture: ComponentFixture<CreateEditClassTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditClassTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditClassTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
