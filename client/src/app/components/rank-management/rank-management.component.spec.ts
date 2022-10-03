import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RankManagementComponent } from './rank-management.component';

describe('RankManagementComponent', () => {
  let component: RankManagementComponent;
  let fixture: ComponentFixture<RankManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RankManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RankManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
