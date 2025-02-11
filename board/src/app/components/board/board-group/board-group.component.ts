import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import { BoardColumnComponent } from '../board-column';
import { ColumnService } from '../services';
import {
  CdkDragDrop,
  DragDropModule,
  moveItemInArray,
} from '@angular/cdk/drag-drop';
import { Column } from '../models';
import { ProgressSpinner } from 'primeng/progressspinner';

@Component({
  selector: 'board-group',
  imports: [DragDropModule, BoardColumnComponent, ProgressSpinner],
  templateUrl: './board-group.component.html',
  styleUrl: './board-group.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardGroupComponent implements OnInit {
  columnService: ColumnService = inject(ColumnService);

  columns$ = this.columnService.columns;

  ngOnInit() {
    this.columnService.getColumns();
  }

  dropColumn($event: CdkDragDrop<Column[]>) {
    moveItemInArray<Column>(
      this.columnService.columns(),
      $event.previousIndex,
      $event.currentIndex,
    );
  }
}
