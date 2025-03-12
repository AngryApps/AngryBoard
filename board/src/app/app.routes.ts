import { Routes } from '@angular/router';
import { BoardGroupComponent } from './components/board';
import { LayoutComponent } from './components/layout/layout/layout.component';
import { LoginComponent } from './components/auth/login/login.component';
import { LoginCallbackComponent } from './components/auth/login-callback/login-callback.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'login-callback',
    component: LoginCallbackComponent,
  },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [{ path: '', component: BoardGroupComponent }],
  },
];
