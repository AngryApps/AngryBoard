import {
  ChangeDetectionStrategy,
  Component,
  output,
  OutputEmitterRef,
} from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'add-card-button',
  imports: [ButtonModule],
  templateUrl: './add-card-button.component.html',
  styleUrl: './add-card-button.component.scss',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AddCardButtonComponent {
  addCard: OutputEmitterRef<void> = output();

  add() {
    this.addCard.emit();
  }
}
