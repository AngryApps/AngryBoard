import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { CardBodyComponent } from '../../card/card-body/card-body.component';
import { Column } from '../models';

@Component({
  selector: 'board-column',
  imports: [CardBodyComponent],
  templateUrl: './board-column.component.html',
  styleUrl: './board-column.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardColumnComponent {
  @Input() column!: Column;
}
