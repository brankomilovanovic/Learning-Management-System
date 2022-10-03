import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditTeachingMaterialComponent } from './create-edit-teaching-material.component';

describe('CreateEditTeachingMaterialComponent', () => {
  let component: CreateEditTeachingMaterialComponent;
  let fixture: ComponentFixture<CreateEditTeachingMaterialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditTeachingMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditTeachingMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
