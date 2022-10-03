import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditTypeRanksComponent } from './create-edit-type-ranks.component';

describe('CreateEditTypeRanksComponent', () => {
  let component: CreateEditTypeRanksComponent;
  let fixture: ComponentFixture<CreateEditTypeRanksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditTypeRanksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditTypeRanksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
