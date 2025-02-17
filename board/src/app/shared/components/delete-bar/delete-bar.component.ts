import {
  ChangeDetectionStrategy,
  Component,
  inject,
  input,
  output,
} from '@angular/core';
import { DrawerModule } from 'primeng/drawer';
import { CdkDragDrop, CdkDragEnter, CdkDropList } from '@angular/cdk/drag-drop';
import { Column } from '../../../components/board';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'delete-bar',
  imports: [DrawerModule, CdkDropList, ConfirmDialogModule, ToastModule],
  templateUrl: './delete-bar.component.html',
  styleUrl: './delete-bar.component.scss',
  standalone: true,
  providers: [ConfirmationService, MessageService],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DeleteBarComponent {
  isVisible = input<boolean>(false);

  deleteConfirm = output<Column>();

  dropListContainer: Column[] = [];

  private confirmationService = inject(ConfirmationService);
  private messageService = inject(MessageService);

  drop(event: CdkDragDrop<Column[]>) {
    const deletedItem = event.previousContainer.data[event.previousIndex];
    event.previousContainer.data.splice(event.previousIndex, 1);

    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this item?',
      icon: 'pi pi-exclamation-triangle',
      header: 'Delete Confirmation',
      position: 'bottom',
      accept: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Confirmed',
          detail: 'The item has been deleted',
        });
        this.deleteConfirm.emit(deletedItem);
      },
      reject: () => {
        this.messageService.add({
          severity: 'contrast',
          summary: 'Undo',
          detail: 'The item has been restored',
        });
        event.previousContainer.data.splice(
          event.previousIndex,
          0,
          deletedItem,
        );
      },
    });
  }

  entered($event: CdkDragEnter<Column[]>) {
    console.log('### (delete-bar.component.ts:52) entered', $event);
  }
}
