import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardGroupComponent } from './board-group.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('BoardGroupComponent', () => {
  let component: BoardGroupComponent;
  let fixture: ComponentFixture<BoardGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BoardGroupComponent,
        HttpClientTestingModule,
        NoopAnimationsModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(BoardGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
