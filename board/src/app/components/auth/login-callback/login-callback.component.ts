import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import { ProgressSpinner } from 'primeng/progressspinner';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { UserService } from '../../../shared/services/user.service';

@Component({
  selector: 'login-callback',
  imports: [ProgressSpinner],
  template: `
    <div class="h-svh w-svw flex flex-col justify-center items-center">
      <p-progress-spinner ariaLabel="loading" />
    </div>
  `,
  standalone: true,
  styles: ``,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginCallbackComponent implements OnInit {
  private authService = inject(AuthService);
  private userService = inject(UserService);
  private router = inject(Router);

  ngOnInit() {
    console.log('### (login-callback.component.ts:26) callback');
    this.authService.checkAuthStatus();
    if (this.userService.user()) {
      this.router.navigate(['/']);
    } else {
      this.authService.logout();
    }
  }
}
