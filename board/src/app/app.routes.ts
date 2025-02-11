import { Routes } from '@angular/router';
import { BoardGroupComponent } from './components/board';
import { LayoutComponent } from './components/layout/layout/layout.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [{ path: '', component: BoardGroupComponent }],
  },
];
