import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
  viewChild,
} from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Menu } from 'primeng/menu';
import { Badge } from 'primeng/badge';
import { Ripple } from 'primeng/ripple';
import { AuthService } from '../../auth/services/auth.service';

@Component({
  selector: 'user-menu',
  imports: [Menu, Badge, Ripple],
  templateUrl: './user-menu.component.html',
  styleUrl: './user-menu.component.scss',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserMenuComponent implements OnInit {
  private authService = inject(AuthService);

  protected items: MenuItem[] | undefined;

  private userMenu = viewChild(Menu);

  ngOnInit() {
    this.items = [
      {
        label: 'Profile',
        items: [
          {
            label: 'Settings',
            icon: 'pi pi-cog',
            // shortcut: '⌘+O'
          },
          {
            label: 'Logout',
            icon: 'pi pi-sign-out',
            // shortcut: '⌘+Q',
            command: () => {
              this.logout();
            },
          },
        ],
      },
    ];
  }

  public toggle($event: Event) {
    this.userMenu()?.toggle($event);
  }

  private logout() {
    this.authService.logout();
  }
}
