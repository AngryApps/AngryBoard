import { ChangeDetectionStrategy, Component, output } from '@angular/core';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzButtonModule } from 'ng-zorro-antd/button';

@Component({
  selector: 'action-menu',
  imports: [NzButtonModule, NzDropDownModule, NzIconModule],
  templateUrl: './action-menu.component.html',
  styleUrl: './action-menu.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ActionMenuComponent {
  edit = output<void>();
  delete = output<void>();

  editAction() {
    this.edit.emit();
  }

  deleteAction() {
    this.delete.emit();
  }
}
