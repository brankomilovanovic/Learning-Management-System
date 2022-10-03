import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { YearManagementComponent } from './year-management.component';

describe('YearManagementComponent', () => {
  let component: YearManagementComponent;
  let fixture: ComponentFixture<YearManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ YearManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YearManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
