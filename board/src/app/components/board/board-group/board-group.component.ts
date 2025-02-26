import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
  signal,
} from '@angular/core';
import { BoardColumnComponent } from '../board-column';
import {
  CdkDragDrop,
  DragDropModule,
  moveItemInArray,
} from '@angular/cdk/drag-drop';
import { Column } from '../models';
import { ButtonModule } from 'primeng/button';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { AddColumnComponent } from '../add-column/add-column.component';
import { BoardService } from '../../../shared';

@Component({
  selector: 'board-group',
  imports: [
    DragDropModule,
    BoardColumnComponent,
    ProgressSpinnerModule,
    ButtonModule,
    AddColumnComponent,
  ],
  templateUrl: './board-group.component.html',
  styleUrl: './board-group.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
})
export class BoardGroupComponent implements OnInit {
  boardService = inject(BoardService);

  columns = this.boardService.columns;
  showDeleteBar = signal(false);

  ngOnInit() {
    this.boardService.getColumns();
  }

  dropColumn($event: CdkDragDrop<Column[]>) {
    moveItemInArray<Column>(
      this.boardService.columns(),
      $event.previousIndex,
      $event.currentIndex,
    );

    if ($event.previousIndex !== $event.currentIndex) {
      const column = this.boardService.columns()[$event.currentIndex];
      const previousColumn =
        this.boardService.columns()[$event.currentIndex - 1];
      const nextColumn = this.boardService.columns()[$event.currentIndex + 1];

      this.boardService.moveColumn(
        column.id,
        previousColumn?.id,
        nextColumn?.id,
      );
    }
  }

  onDragStart() {
    this.showDeleteBar.set(true);
  }

  onDragEnd() {
    this.showDeleteBar.set(false);
  }

  deleteColumn(column: Column) {
    this.boardService.deleteColumn(column.id);
  }
}
