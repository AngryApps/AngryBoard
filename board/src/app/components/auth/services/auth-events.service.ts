import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthEventsService {
  private authErrorSignal = signal<boolean>(false);
  authError = this.authErrorSignal.asReadonly();

  emitAuthError() {
    this.authErrorSignal.set(true);
  }
}
