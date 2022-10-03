import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutSubjectComponent } from './about-subject.component';

describe('AboutSubjectComponent', () => {
  let component: AboutSubjectComponent;
  let fixture: ComponentFixture<AboutSubjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AboutSubjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AboutSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
