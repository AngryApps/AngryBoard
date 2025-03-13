import { effect, inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { catchError, of, tap } from 'rxjs';
import { UserService } from '../../../shared/services/user.service';
import { AuthEventsService } from './auth-events.service';
import { BaseApiHttpRequestService } from '../../../shared';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiService = inject(BaseApiHttpRequestService);

  userService = inject(UserService);
  authEventsService = inject(AuthEventsService);

  private http: HttpClient = inject(HttpClient);
  private router: Router = inject(Router);

  constructor() {
    this.checkAuthStatus();

    effect(() => {
      if (this.authEventsService.authError()) {
        this.logout();
      }
    });
  }

  login(): void {
    window.location.href = 'http://localhost:8080/oauth2/authorization/github';
  }

  checkAuthStatus(): void {
    this.userService.getUser();
  }

  logout(): void {
    this.apiService
      .get('logout')
      .pipe(
        tap(() => {
          this.router.navigate(['/login']);
        }),
        catchError(() => {
          this.router.navigate(['/login']);
          return of(null);
        }),
      )
      .subscribe();
  }
}
