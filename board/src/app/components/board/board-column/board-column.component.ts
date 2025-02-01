import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
} from '@angular/core';
import { Column } from '../models';
import { CardBodyComponent } from '../../card';
import {
  CdkDrag,
  CdkDragDrop,
  CdkDropList,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { Card } from '../../card/models/card';

@Component({
  selector: 'board-column',
  imports: [CardBodyComponent, CdkDrag, CdkDropList],
  templateUrl: './board-column.component.html',
  styleUrl: './board-column.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardColumnComponent {
  column = input.required<Column>({});

  name = computed(() => this.column().name);
  description = computed<string>(() => this.column().description);
  cards = computed<Card[]>(() => this.column().cards);

  dropCard($event: CdkDragDrop<Card[]>) {
    if ($event.previousContainer === $event.container) {
      moveItemInArray(
        $event.container.data,
        $event.previousIndex,
        $event.currentIndex,
      );
    } else {
      transferArrayItem(
        $event.previousContainer.data,
        $event.container.data,
        $event.previousIndex,
        $event.currentIndex,
      );
    }
  }
}
