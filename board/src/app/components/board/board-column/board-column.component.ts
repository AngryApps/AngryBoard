import {
  ChangeDetectionStrategy,
  Component,
  inject,
  input,
  signal,
} from '@angular/core';
import { Column } from '../models';
import {
  CdkDrag,
  CdkDragDrop,
  CdkDropList,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { Card } from '../../card/models/card';
import { PopoverModule } from 'primeng/popover';
import { CommonModule } from '@angular/common';
import { ActionMenuComponent } from '../action-menu/action-menu.component';
import { CardBodyComponent } from '../../card';
import { BoardService, InplaceInputComponent } from '../../../shared';
import { AddCardButtonComponent } from '../../card/add-card-button/add-card-button.component';

@Component({
  selector: 'board-column',
  standalone: true,
  imports: [
    CommonModule,
    CdkDrag,
    CdkDropList,
    PopoverModule,
    ActionMenuComponent,
    CardBodyComponent,
    InplaceInputComponent,
    AddCardButtonComponent,
  ],
  templateUrl: './board-column.component.html',
  styleUrl: './board-column.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardColumnComponent {
  boardService = inject(BoardService);

  column = input.required<Column>({});

  protected newCards = signal<Card[]>([]);

  dropCard($event: CdkDragDrop<Card[]>) {
    if ($event.previousContainer === $event.container) {
      moveItemInArray(
        $event.container.data,
        $event.previousIndex,
        $event.currentIndex,
      );
    } else {
      transferArrayItem(
        $event.previousContainer.data,
        $event.container.data,
        $event.previousIndex,
        $event.currentIndex,
      );
    }

    if (
      $event.previousIndex !== $event.currentIndex ||
      $event.previousContainer !== $event.container
    ) {
      const currentCard = this.column().cards[$event.currentIndex];

      const previousCard = this.column().cards[$event.currentIndex - 1];

      const nextCard = this.column().cards[$event.currentIndex + 1];

      const targetColumn = this.column();

      this.boardService.moveCard(
        currentCard.id,
        previousCard?.id,
        nextCard?.id,
        targetColumn.id,
      );
    }
  }

  onEdit() {
    console.log('not implemented');
  }

  onDelete() {
    this.boardService.deleteColumn(this.column().id);
  }

  editDescription(description: string) {
    this.boardService.editColumn(
      this.column().id,
      this.column().title,
      description,
    );
  }

  editTitle(title: string) {
    this.boardService.editColumn(
      this.column().id,
      title,
      this.column().description,
    );
  }

  onAddCard() {
    const card = {
      title: 'New Card',
    } as Card;

    this.newCards.set([...this.newCards(), card]);
  }

  saveNewCard(card: Card, idx: number) {
    this.newCards.set(this.newCards().filter((_, i) => i !== idx));
    if (!card?.title) {
      return;
    }

    this.boardService.addCard(this.column().id, card.title);
  }
}
