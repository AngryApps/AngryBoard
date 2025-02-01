import { DestroyRef, Injectable, signal } from '@angular/core';
import { map, Observable, of, Subject, take, throwError } from 'rxjs';
import { Column } from '../models';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

interface ColumnWS {
  id: string;
  name: string;
  description: string;
  position: number;
  created_at: Date;
  updated_at: Date;
}

@Injectable({
  providedIn: 'root',
})
export class ColumnService {
  KANBAN_COLUMNS: ColumnWS[] = [
    {
      id: '3f9502ab-1893-4e8e-a3d2-901d55b234c7',
      name: 'Backlog',
      description: 'Upcoming tasks and features',
      position: 1,
      created_at: new Date('2024-03-01T09:00:00Z'),
      updated_at: new Date('2024-03-10T14:00:00Z'),
    },
    {
      id: '6b21c952-3e74-4a1d-b4d1-8a3f5b6c7d8e',
      name: 'In Progress',
      description: 'Active development stage',
      position: 2,
      created_at: new Date('2024-03-01T09:00:00Z'),
      updated_at: new Date('2024-03-12T16:30:00Z'),
    },
    {
      id: 'c45d9e2f-1a2b-4c3d-9e8f-7a6b5c4d3e2f',
      name: 'Review',
      description: 'Quality assurance phase',
      position: 3,
      created_at: new Date('2024-03-01T09:00:00Z'),
      updated_at: new Date('2024-03-14T11:15:00Z'),
    },
    {
      id: 'd8e7f6a5-4b3c-2d1e-9f0a-8b7c6d5e4f3a',
      name: 'Testing',
      description: 'User acceptance testing',
      position: 4,
      created_at: new Date('2024-03-01T09:00:00Z'),
      updated_at: new Date('2024-03-16T15:45:00Z'),
    },
    {
      id: 'e9f8a7b6-5c4d-3e2f-1a0b-9c8d7e6f5a4b',
      name: 'Done',
      description: 'Ready for deployment',
      position: 5,
      created_at: new Date('2024-03-01T09:00:00Z'),
      updated_at: new Date('2024-03-18T10:20:00Z'),
    },
  ];

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
        map((columns) =>
          columns.map(
            (column: {
              id: string;
              name: string;
              description: string;
              position: number;
              created_at: Date;
              updated_at: Date;
            }) => ({
              id: column.id,
              name: column.name,
              description: column.description,
              position: column.position,
              createdAt: column.created_at,
              updatedAt: column.updated_at,
            }),
          ),
        ),
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

  private fakeHttpRequest(): Observable<ColumnWS[]> {
    // Simulate random success/error
    const shouldFail = Math.random() < 0.2;

    return shouldFail
      ? throwError(() => new Error('API Error'))
      : of(this.KANBAN_COLUMNS);
  }
}
