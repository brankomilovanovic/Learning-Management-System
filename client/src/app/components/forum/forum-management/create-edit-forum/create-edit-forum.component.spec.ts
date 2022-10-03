import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditForumComponent } from './create-edit-forum.component';

describe('CreateEditForumComponent', () => {
  let component: CreateEditForumComponent;
  let fixture: ComponentFixture<CreateEditForumComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEditForumComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEditForumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
