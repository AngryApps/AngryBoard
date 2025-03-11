import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BaseApiHttpRequestService } from '../../../shared';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiService = inject(BaseApiHttpRequestService);

  router = inject(Router);

  login() {
    this.apiService.get<void>('/oauth2/authorization/github');
  }

  logout() {
    this.router.navigate(['/login']);
  }

  handleAuthenticationResponse(token: string): void {
    localStorage.setItem('auth_token', token);
    this.router.navigate(['/']);
  }

  isAuthenticated(): boolean {
    return this.hasToken();
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('auth_token');
  }
}
