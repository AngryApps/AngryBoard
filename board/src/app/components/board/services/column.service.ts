import { DestroyRef, Injectable, signal } from '@angular/core';
import { delay, map, Observable, of, Subject, take } from 'rxjs';
import { Column, ColumnWS, KANBAN_COLUMNS } from '../models';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CardWS } from '../../card/models/card';

@Injectable({
  providedIn: 'root',
})
export class ColumnService {
  private columnSignal = signal<Column[]>([]);
  private loadingSignal = signal<boolean>(false);
  private errorSignal = signal<string | null>(null);
  private destroy$ = new Subject<void>();

  columns = this.columnSignal.asReadonly();
  isLoading = this.loadingSignal.asReadonly();
  error = this.errorSignal.asReadonly();

  constructor(private destroyRef: DestroyRef) {
    destroyRef.onDestroy(() => {
      this.destroy$.next();
      this.destroy$.complete();
    });
  }

  getColumns(): void {
    if (this.isLoading()) return;

    this.loadingSignal.set(true);
    this.errorSignal.set(null);

    this.fakeHttpRequest()
      .pipe(
        take(1),
        takeUntilDestroyed(this.destroyRef),
        map((columns: ColumnWS[]) =>
          columns.map((column: ColumnWS) => ({
            id: column.id,
            name: column.name,
            description: column.description,
            position: column.position,
            createdAt: column.created_at,
            updatedAt: column.updated_at,
            cards: column.cards?.map((card: CardWS) => ({
              id: card.id,
              title: card.title,
              description: card.description,
              createdAt: card.created_at,
              updatedAt: card.updated_at,
              columnId: card.column_id,
              columnPosition: card.column_position,
              attachments: card.attachments,
            })),
          })),
        ),
      )
      .subscribe({
        next: (columns) => {
          this.columnSignal.set(columns as Column[]);
          this.loadingSignal.set(false);
        },
        error: (err: unknown) => {
          this.errorSignal.set(`Failed to load columns ${err}`);
          this.loadingSignal.set(false);
        },
        complete: () => {
          this.loadingSignal.set(false);
        },
      });
  }

  private fakeHttpRequest(): Observable<ColumnWS[]> {
    return of(KANBAN_COLUMNS).pipe(delay(800));
  }
}
