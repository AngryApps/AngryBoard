import { ChangeDetectionStrategy, Component } from '@angular/core';
import { BoardColumnComponent } from '../board-column/board-column.component';

@Component({
  selector: 'board-group',
  imports: [BoardColumnComponent],
  templateUrl: './board-group.component.html',
  styleUrl: './board-group.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardGroupComponent {}
