import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, of, tap } from 'rxjs';
import { UserService } from '../../../shared/services/user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  userService = inject(UserService);

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);

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

  login(): void {
    window.location.href = 'http://localhost:8080/oauth2/authorization/github';
  }

  checkAuthStatus(): void {
    this.userService.getUser();
  }

  logout(): void {
    this.http
      .post('user/logout', { withCredentials: true })
      .pipe(
        tap(() => {
          this.isAuthenticatedSubject.next(false);
          this.router.navigate(['/login']);
        }),
        catchError(() => {
          this.isAuthenticatedSubject.next(false);
          this.router.navigate(['/login']);
          return of(null);
        }),
      )
      .subscribe();
  }
}
