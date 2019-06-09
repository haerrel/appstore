import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowProblemComponent } from './show-problem.component';

describe('ShowProblemComponent', () => {
  let component: ShowProblemComponent;
  let fixture: ComponentFixture<ShowProblemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowProblemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowProblemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
