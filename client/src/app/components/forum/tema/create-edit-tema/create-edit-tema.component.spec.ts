import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditTemaComponent } from './create-edit-tema.component';

describe('CreateEditTemaComponent', () => {
  let component: CreateEditTemaComponent;
  let fixture: ComponentFixture<CreateEditTemaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditTemaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditTemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
