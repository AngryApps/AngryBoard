import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'card-body',
  imports: [],
  templateUrl: './card-body.component.html',
  styleUrl: './card-body.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CardBodyComponent {}
