import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BaseApiHttpRequestService } from '../../../shared';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiService = inject(BaseApiHttpRequestService);

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private userSubject = new BehaviorSubject<unknown>(null);

  private http: HttpClient = inject(HttpClient);
  private router: Router = inject(Router);

  constructor() {
    this.checkAuthStatus();
  }

  get isAuthenticated$(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  get isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  get user$(): Observable<unknown> {
    return this.userSubject.asObservable();
  }

  login(): void {
    window.location.href = 'http://localhost:8080/oauth2/authorization/github';
  }

  checkAuthStatus(): void {
    this.apiService
      .get('/api/user')
      .pipe(
        tap((user) => {
          this.userSubject.next(user);
          this.isAuthenticatedSubject.next(true);
        }),
        catchError(() => {
          this.userSubject.next(null);
          this.isAuthenticatedSubject.next(false);
          return of(null);
        }),
      )
      .subscribe();
  }

  logout(): void {
    this.http
      .post('/logout', { withCredentials: true })
      .pipe(
        tap(() => {
          this.userSubject.next(null);
          this.isAuthenticatedSubject.next(false);
          this.router.navigate(['/login']);
        }),
        catchError(() => {
          this.userSubject.next(null);
          this.isAuthenticatedSubject.next(false);
          this.router.navigate(['/login']);
          return of(null);
        }),
      )
      .subscribe();
  }
}
