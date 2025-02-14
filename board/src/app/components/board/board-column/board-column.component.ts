import {
  ChangeDetectionStrategy,
  Component,
  inject,
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
import { ActionMenuComponent } from '../action-menu/action-menu.component';
import { ColumnService } from '../services';
import { PopoverModule } from 'primeng/popover';

@Component({
  selector: 'board-column',
  imports: [
    CardBodyComponent,
    CdkDrag,
    CdkDropList,
    ActionMenuComponent,
    PopoverModule,
  ],
  templateUrl: './board-column.component.html',
  styleUrl: './board-column.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardColumnComponent {
  columnService = inject(ColumnService);
  column = input.required<Column>({});

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

  onEdit() {
    console.log('not implemented');
  }

  onDelete() {
    this.columnService.deleteColumn(this.column().id);
  }
}
