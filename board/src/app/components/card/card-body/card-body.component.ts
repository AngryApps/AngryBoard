import {
  ChangeDetectionStrategy,
  Component,
  computed,
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
})
export class CardBodyComponent {
  card = input<Card>();

  title = computed(() => this.card()?.title);
  description = computed(() => this.card()?.description);
}
