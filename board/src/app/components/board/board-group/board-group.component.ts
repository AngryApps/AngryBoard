import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import { BoardColumnComponent } from '../board-column/board-column.component';
import { ColumnService } from '../services/column.service';
import { NzSpinModule } from 'ng-zorro-antd/spin';

@Component({
  selector: 'board-group',
  imports: [BoardColumnComponent, NzSpinModule],
  templateUrl: './board-group.component.html',
  styleUrl: './board-group.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardGroupComponent implements OnInit {
  columnService: ColumnService = inject(ColumnService);

  columns$ = this.columnService.columns;

  constructor(private cd: ChangeDetectorRef) {}

  ngOnInit() {
    this.columnService.getColumns();
  }
}
