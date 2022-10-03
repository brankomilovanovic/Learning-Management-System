import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersManagementComponent } from './users-management-table.component';

describe('UsersManagementComponent', () => {
  let component: UsersManagementComponent;
  let fixture: ComponentFixture<UsersManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
