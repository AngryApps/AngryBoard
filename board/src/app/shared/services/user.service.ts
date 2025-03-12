import { DestroyRef, inject, Injectable, signal } from '@angular/core';
import { map, Subject } from 'rxjs';
import { BaseApiHttpRequestService } from './base-api-http-request.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { BaseResponse, User, UserResponse } from '../models';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  apiService = inject(BaseApiHttpRequestService);

  private userSignal = signal<User>({} as User);
  user = this.userSignal.asReadonly();

  private destroy$ = new Subject<void>();

  constructor(private destroyRef: DestroyRef) {
    destroyRef.onDestroy(() => {
      this.destroy$.next();
      this.destroy$.complete();
    });
  }

  getUser(): void {
    this.apiService
      .get<UserResponse>('auth/current')
      .pipe(
        takeUntilDestroyed(this.destroyRef),
        map((response: BaseResponse<UserResponse>) => {
          if (!response.success) {
            throw new Error(response.message);
          }

          return this.parseUserResponse(response.data);
        }),
      )
      .subscribe({
        next: (user: User) => {
          this.userSignal.set(user);
        },
        error: (err: string) => {
          console.error(err);
        },
      });
  }

  private parseUserResponse(response: UserResponse): User {
    return {
      id: response.id,
      email: response.email,
      name: response.name,
      avatar: response.avatar,
    };
  }
}
