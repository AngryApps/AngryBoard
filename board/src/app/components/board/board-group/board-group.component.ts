import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
  signal,
} from '@angular/core';
import { BoardColumnComponent } from '../board-column';
import { ColumnService } from '../services';
import {
  CdkDragDrop,
  DragDropModule,
  moveItemInArray,
} from '@angular/cdk/drag-drop';
import { Column } from '../models';
import { ButtonModule } from 'primeng/button';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { AddColumnComponent } from '../add-column/add-column.component';

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
  columnService = inject(ColumnService);

  columns = this.columnService.columns;
  showDeleteBar = signal(false);

  ngOnInit() {
    this.columnService.getColumns();
  }

  dropColumn($event: CdkDragDrop<Column[]>) {
    moveItemInArray<Column>(
      this.columnService.columns(),
      $event.previousIndex,
      $event.currentIndex,
    );

    if ($event.previousIndex !== $event.currentIndex) {
      const column = this.columnService.columns()[$event.currentIndex];
      this.columnService.editColumn(
        column.id,
        column.title,
        column.description,
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
    this.columnService.deleteColumn(column.id);
  }
}
