import { ChangeDetectionStrategy, Component, output } from '@angular/core';
import { Menu } from 'primeng/menu';
import { Button } from 'primeng/button';
import { MenuItem, PrimeIcons } from 'primeng/api';

@Component({
  selector: 'action-menu',
  imports: [Menu, Button],
  templateUrl: './action-menu.component.html',
  styleUrl: './action-menu.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ActionMenuComponent {
  edit = output<void>();
  delete = output<void>();

  items: MenuItem[] = [
    {
      label: 'Actions',
      items: [
        {
          label: 'Edit',
          icon: PrimeIcons.PENCIL,
          command: () => this.editAction(),
        },
        {
          label: 'Delete',
          icon: PrimeIcons.TRASH,
          command: () => this.deleteAction(),
        },
      ],
    },
  ];

  editAction() {
    this.edit.emit();
  }

  deleteAction() {
    this.delete.emit();
  }
}
