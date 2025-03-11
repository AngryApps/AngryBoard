import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Button } from 'primeng/button';

@Component({
  selector: 'login',
  imports: [Button],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  githubLogin() {
    window.location.href = 'https://github.com/oauth2/v1/authorize';
  }
}
