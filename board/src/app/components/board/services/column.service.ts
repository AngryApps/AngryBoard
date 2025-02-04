import { DestroyRef, inject, Injectable, signal } from '@angular/core';
import { map, Subject, take } from 'rxjs';
import { Column, ColumnResponse } from '../models';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { BaseApiHttpRequestService, BaseResponse } from '../../../shared';
import { Card, CardWS } from '../../card/models/card';

@Injectable({
  providedIn: 'root',
})
export class ColumnService {
  apiService = inject(BaseApiHttpRequestService);

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

    this.apiService
      .get<ColumnResponse[]>('columns')
      .pipe(
        take(1),
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

  parseColumnResponse(response: ColumnResponse): Column {
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

  parseCardResponse(response: CardWS): Card {
    return {
      id: response.id,
      title: response.title,
      description: response.description,
      columnPosition: response.column_position,
      columnId: response.column_id,
      attachments: response.attachments,
      createdAt: response.created_at,
      updatedAt: response.updated_at,
    };
  }
}
