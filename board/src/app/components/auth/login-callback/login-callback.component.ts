import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import { ProgressSpinner } from 'primeng/progressspinner';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'login-callback',
  imports: [ProgressSpinner],
  template: `
    <div class="h-svh w-svw flex flex-col justify-center items-center">
      <p-progress-spinner ariaLabel="loading" />
    </div>
  `,
  styles: ``,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginCallbackComponent implements OnInit {
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      const token = params['token'];

      if (token) {
        this.authService.handleAuthenticationResponse(token);
      } else {
        this.router.navigate(['/login']);
      }
    });
  }
}
