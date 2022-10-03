import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeRanksManagementComponent } from './type-ranks-management.component';

describe('TypeRanksManagementComponent', () => {
  let component: TypeRanksManagementComponent;
  let fixture: ComponentFixture<TypeRanksManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TypeRanksManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeRanksManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
