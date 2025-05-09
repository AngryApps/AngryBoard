import { DestroyRef, inject, Injectable, signal } from '@angular/core';
import { map, Subject } from 'rxjs';
import { BaseApiHttpRequestService } from './base-api-http-request.service';
import {
  AddColumnRequest,
  Column,
  ColumnResponse,
  MoveColumnRequest,
  UpdateColumnRequest,
} from '../../components/board';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { BaseResponse } from '../models';
import {
  AddCardRequest,
  Card,
  CardResponse,
  EditCardRequest,
  MoveCardRequest,
} from '../../components/card/models/card';

@Injectable({
  providedIn: 'root',
})
export class BoardService {
  apiService = inject(BaseApiHttpRequestService);

  private columnSignal = signal<Column[]>([]);
  columns = this.columnSignal.asReadonly();
  private loadingSignal = signal<boolean>(false);
  isLoading = this.loadingSignal.asReadonly();
  private errorSignal = signal<string | null>(null);
  error = this.errorSignal.asReadonly();
  private destroy$ = new Subject<void>();

  constructor(private destroyRef: DestroyRef) {
    destroyRef.onDestroy(() => {
      this.destroy$.next();
      this.destroy$.complete();
    });
  }

  getColumns(): void {
    if (this.isLoading()) return;

    this.loadingSignal.update(() => true);
    this.errorSignal.update(() => null);

    this.apiService
      .get<ColumnResponse[]>('columns')
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<ColumnResponse[]>) => {
          if (!response.success) {
            throw new Error(response.message);
          }
          return response.data.map((column) =>
            this.parseColumnResponse(column),
          );
        }),
      )
      .subscribe({
        next: (columns: Column[]) => {
          this.columnSignal.set(columns);
          this.loadingSignal.update(() => false);
        },
        error: (err: string) => {
          this.errorSignal.set(`Failed to load columns ${err}`);
          this.loadingSignal.update(() => false);
        },
      });
  }

  addColumn(title: string, description?: string): void {
    if (this.isLoading()) return;

    this.loadingSignal.update(() => true);
    this.errorSignal.update(() => null);

    const addColumnRequest: AddColumnRequest = {
      title,
      description: description || undefined,
      position: this.columns().length,
    };

    this.apiService
      .post<ColumnResponse>('columns', addColumnRequest)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<ColumnResponse>) => {
          if (!response.success) {
            throw new Error(response.message);
          }

          return this.parseColumnResponse(response.data);
        }),
      )
      .subscribe({
        next: (column: Column) => {
          this.columnSignal.update((columns) => [...columns, column]);
          this.loadingSignal.update(() => false);
        },
        error: (err: string) => {
          this.errorSignal.set(`Failed to add column ${err}`);
          this.loadingSignal.update(() => false);
        },
      });
  }

  editColumn(columnId: string, title: string, description: string): void {
    if (this.isLoading()) return;

    this.loadingSignal.update(() => true);
    this.errorSignal.update(() => null);

    const editColumnRequest: UpdateColumnRequest = {
      id: columnId,
      title,
      description: description,
    };

    this.apiService
      .patch<ColumnResponse>(`columns`, editColumnRequest.id, editColumnRequest)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<ColumnResponse>) => {
          if (!response.success) {
            throw new Error(response.message);
          }

          return this.parseColumnResponse(response.data);
        }),
      )
      .subscribe({
        next: (updatedColumn: Column) => {
          this.columnSignal.update((columns) =>
            columns.map((c) => {
              if (c.id === updatedColumn.id) {
                return {
                  ...updatedColumn,
                  cards: c.cards,
                };
              }
              return c;
            }),
          );
          this.loadingSignal.update(() => false);
        },
        error: (err: string) => {
          this.errorSignal.set(`Failed to edit column ${err}`);
          this.loadingSignal.update(() => false);
        },
      });
  }

  deleteColumn(columnId: string): void {
    if (this.isLoading()) return;

    this.loadingSignal.update(() => true);
    this.errorSignal.update(() => null);

    this.apiService
      .delete<ColumnResponse>('columns', columnId)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<ColumnResponse>) => {
          if (!response.success) {
            throw new Error(response.message);
          }
        }),
      )
      .subscribe({
        next: () => {
          this.columnSignal.update((columns) =>
            columns.filter((column) => column.id !== columnId),
          );
          this.loadingSignal.update(() => false);
        },
        error: (err: string) => {
          this.errorSignal.set(`Failed to delete column ${err}`);
          this.loadingSignal.update(() => false);
        },
      });
  }

  addCard(columnId: string, title: string, description?: string): void {
    if (this.isLoading()) return;

    this.loadingSignal.update(() => true);
    this.errorSignal.update(() => null);

    const addCardRequest: AddCardRequest = {
      title,
      description: description || undefined,
      columnId,
    };

    this.apiService
      .post<CardResponse>('cards', addCardRequest)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<CardResponse>) => {
          if (!response.success) {
            throw new Error(response.message);
          }

          return this.parseCardResponse(response.data);
        }),
      )
      .subscribe({
        next: (card: Card) => {
          this.columnSignal.update((columns) =>
            columns.map((column) => {
              if (column.id === columnId) {
                return {
                  ...column,
                  cards: [card, ...column.cards],
                };
              }
              return column;
            }),
          );
          this.loadingSignal.update(() => false);
        },
        error: (err: string) => {
          this.errorSignal.set(`Failed to add column ${err}`);
          this.loadingSignal.update(() => false);
        },
      });
  }

  editCard(
    columnId: string,
    id: string,
    title: string,
    description: string,
    position: number,
  ): void {
    if (this.isLoading()) return;

    this.loadingSignal.update(() => true);
    this.errorSignal.update(() => null);

    const editCardRequest: EditCardRequest = {
      id,
      title,
      description,
      position,
      columnId,
    };

    this.apiService
      .patch<CardResponse>('cards', id, editCardRequest)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<CardResponse>) => {
          if (!response.success) {
            throw new Error(response.message);
          }

          return this.parseCardResponse(response.data);
        }),
      )
      .subscribe({
        next: (card: Card) => {
          this.columnSignal.update((columns) =>
            columns.map((column) => {
              return {
                ...column,
                cards: column.cards.map((c) => {
                  if (c.id === card.id) {
                    return card;
                  }
                  return c;
                }),
              };
            }),
          );
          this.loadingSignal.update(() => false);
        },
        error: (err: string) => {
          this.errorSignal.set(`Failed to edit card ${err}`);
          this.loadingSignal.update(() => false);
        },
      });
  }

  async moveCard(
    cardId: string,
    previousCardId: string,
    nextCardId: string,
    targetColumnId: string,
  ) {
    if (this.isLoading()) return;

    this.loadingSignal.update(() => true);
    this.errorSignal.update(() => null);

    const moveCardRequest: MoveCardRequest = {
      targetColumnId,
    };

    if (previousCardId) {
      moveCardRequest.previousCardId = previousCardId;
    }

    if (nextCardId) {
      moveCardRequest.nextCardId = nextCardId;
    }

    this.apiService
      .patch<CardResponse>('cards/changePosition', cardId, moveCardRequest)
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<CardResponse>) => {
          if (!response.success) {
            throw new Error(response.message);
          }

          return this.parseCardResponse(response.data);
        }),
      )
      .subscribe({
        next: (card: Card) => {
          this.columnSignal.update((columns) =>
            columns.map((column) => {
              return {
                ...column,
                cards: column.cards.map((c) => {
                  if (c.id === card.id) {
                    return card;
                  }
                  return c;
                }),
              };
            }),
          );
          this.loadingSignal.update(() => false);
        },
        error: (err: string) => {
          this.errorSignal.set(`Failed to move card ${err}`);
          this.loadingSignal.update(() => false);
        },
      });
  }

  moveColumn(columnId: string, previousColumnId: string, nextColumnId: string) {
    if (this.isLoading()) return;

    this.loadingSignal.update(() => true);
    this.errorSignal.update(() => null);

    const moveColumnRequest = {} as MoveColumnRequest;

    if (columnId) {
      moveColumnRequest.previousColumnId = previousColumnId;
    }

    if (nextColumnId) {
      moveColumnRequest.nextColumnId = nextColumnId;
    }

    this.apiService
      .patch<ColumnResponse>(
        `columns/changePosition`,
        columnId,
        moveColumnRequest,
      )
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<ColumnResponse>) => {
          if (!response.success) {
            throw new Error(response.message);
          }

          return this.parseColumnResponse(response.data);
        }),
      )
      .subscribe({
        next: (column: Column) => {
          this.columnSignal.update((columns) =>
            columns.map((c) => {
              if (c.id === column.id) {
                return column;
              }
              return c;
            }),
          );
          this.loadingSignal.update(() => false);
        },
        error: (err: string) => {
          this.errorSignal.set(`Failed to move column ${err}`);
          this.loadingSignal.update(() => false);
        },
      });
  }

  private parseColumnResponse(response: ColumnResponse): Column {
    return {
      id: response.id,
      title: response.title,
      description: response.description,
      position: response.position,
      createdAt: response.created_at,
      updatedAt: response.updated_at,
      cards: response.cards?.map((card) => this.parseCardResponse(card)) || [],
    };
  }

  private parseCardResponse(response: CardResponse): Card {
    return {
      id: response.id,
      title: response.title,
      description: response.description,
      position: response.position,
      columnId: response.column_id,
      createdAt: response.created_at,
      updatedAt: response.updated_at,
    };
  }
}
