import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditRankComponent } from './create-edit-rank.component';

describe('CreateEditRankComponent', () => {
  let component: CreateEditRankComponent;
  let fixture: ComponentFixture<CreateEditRankComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditRankComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditRankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
