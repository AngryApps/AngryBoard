import { ChangeDetectionStrategy, Component } from '@angular/core';
import { CardBodyComponent } from '../../card/card-body/card-body.component';

@Component({
  selector: 'board-column',
  imports: [CardBodyComponent],
  templateUrl: './board-column.component.html',
  styleUrl: './board-column.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardColumnComponent {}
