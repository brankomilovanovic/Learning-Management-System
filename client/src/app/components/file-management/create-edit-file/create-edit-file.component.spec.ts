import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditFileComponent } from './create-edit-file.component';

describe('CreateEditFileComponent', () => {
  let component: CreateEditFileComponent;
  let fixture: ComponentFixture<CreateEditFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
