import {
  ChangeDetectionStrategy,
  Component,
  output,
  OutputEmitterRef,
} from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'add-card',
  imports: [ButtonModule],
  templateUrl: './add-card.component.html',
  styleUrl: './add-card.component.scss',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AddCardComponent {
  addCard: OutputEmitterRef<void> = output();

  add() {
    this.addCard.emit();
  }
}
