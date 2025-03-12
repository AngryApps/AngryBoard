import {
  ChangeDetectionStrategy,
  Component,
  inject,
  signal,
} from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { MenubarModule } from 'primeng/menubar';
import { CommonModule } from '@angular/common';
import { Avatar } from 'primeng/avatar';
import { UserMenuComponent } from '../user-menu/user-menu.component';
import { UserService } from '../../../shared/services/user.service';
import { Ripple } from 'primeng/ripple';

@Component({
  selector: 'topbar',
  imports: [
    CommonModule,
    MenubarModule,
    ButtonModule,
    Avatar,
    UserMenuComponent,
    Ripple,
  ],
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TopbarComponent {
  userService = inject(UserService);
  user = this.userService.user;

  iconClass = signal<string>('pi pi-moon');

  items = [
    {
      label: 'Home',
      icon: 'pi pi-home',
    },
  ];

  toggleDarkMode() {
    const element = document.querySelector('html');
    if (element !== null) {
      element.classList.toggle('my-app-dark');
      this.iconClass.set(
        element.classList.contains('my-app-dark') ? 'pi pi-sun' : 'pi pi-moon',
      );
    }
  }
}
