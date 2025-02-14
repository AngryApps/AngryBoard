import {
  ChangeDetectionStrategy,
  Component,
  inject,
  output,
} from '@angular/core';
import { Menu } from 'primeng/menu';
import { Button } from 'primeng/button';
import {
  ConfirmationService,
  MenuItem,
  MessageService,
  PrimeIcons,
} from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

@Component({
  selector: 'action-menu',
  imports: [Menu, Button, ToastModule, ConfirmDialogModule],
  providers: [ConfirmationService, MessageService],
  templateUrl: './action-menu.component.html',
  styleUrl: './action-menu.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ActionMenuComponent {
  public edit = output<void>();
  public delete = output<void>();

  protected items: MenuItem[] = [
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

  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);

  editAction() {
    this.edit.emit();
  }

  deleteAction() {
    this.confirmationService.confirm({
      message: 'Do you want to delete this column?',
      icon: 'pi pi-info-circle',
      rejectButtonProps: {
        label: 'Cancel',
        severity: 'secondary',
        outlined: true,
      },
      acceptButtonProps: {
        label: 'Delete',
        severity: 'danger',
      },
      accept: () => {
        this.delete.emit();
        this.messageService.add({
          severity: 'info',
          summary: 'Confirmed',
          detail: 'Record deleted',
          life: 3000,
        });
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Rejected',
          detail: 'You have rejected',
          life: 3000,
        });
      },
    });
  }
}
