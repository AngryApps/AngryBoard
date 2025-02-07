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
import { ProgressSpinner } from 'primeng/progressspinner';
import { Button } from 'primeng/button';

@Component({
  selector: 'board-group',
  imports: [DragDropModule, BoardColumnComponent, ProgressSpinner, Button],
  templateUrl: './board-group.component.html',
  styleUrl: './board-group.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardGroupComponent implements OnInit {
  columnService: ColumnService = inject(ColumnService);

  columns$ = this.columnService.columns;
  iconClass = signal<string>('pi pi-moon');

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

  toggleDarkMode() {
    const element = document.querySelector('html');
    if (element !== null) {
      element.classList.toggle('my-app-dark');
      this.iconClass.set(
        element.classList.contains('my-app-dark') ? 'pi pi-sun' : 'pi pi-moon',
      );
    }
  }
}
