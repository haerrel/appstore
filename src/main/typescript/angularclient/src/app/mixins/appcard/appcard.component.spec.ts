import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppcardComponent } from './appcard.component';

describe('AppcardComponent', () => {
  let component: AppcardComponent;
  let fixture: ComponentFixture<AppcardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppcardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppcardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
