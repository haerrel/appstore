import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerticalAppListComponent } from './vertical-app-list.component';

describe('VerticalAppListComponent', () => {
  let component: VerticalAppListComponent;
  let fixture: ComponentFixture<VerticalAppListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerticalAppListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerticalAppListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
