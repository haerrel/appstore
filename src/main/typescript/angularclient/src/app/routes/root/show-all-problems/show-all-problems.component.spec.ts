import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowAllProblemsComponent } from './show-all-problems.component';

describe('ShowAllProblemsComponent', () => {
  let component: ShowAllProblemsComponent;
  let fixture: ComponentFixture<ShowAllProblemsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowAllProblemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowAllProblemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
