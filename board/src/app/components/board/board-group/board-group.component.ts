import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import { BoardColumnComponent } from '../board-column';
import { ColumnService } from '../services';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import {
  CdkDragDrop,
  DragDropModule,
  moveItemInArray,
} from '@angular/cdk/drag-drop';
import { Column } from '../models';

@Component({
  selector: 'board-group',
  imports: [DragDropModule, BoardColumnComponent, NzSpinModule],
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
