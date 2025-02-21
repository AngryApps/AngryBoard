import {
  ChangeDetectionStrategy,
  Component,
  input,
  ViewEncapsulation,
} from '@angular/core';
import { Card } from '../models/card';

@Component({
  selector: 'card-body',
  imports: [],
  templateUrl: './card-body.component.html',
  styleUrl: './card-body.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None,
  standalone: true,
})
export class CardBodyComponent {
  card = input<Card>();
}
