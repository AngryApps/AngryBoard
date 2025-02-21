import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  model,
  output,
  signal,
  viewChild,
} from '@angular/core';
import { Inplace, InplaceModule } from 'primeng/inplace';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonGroupModule } from 'primeng/buttongroup';
import { CheckIcon, TimesIcon } from 'primeng/icons';
import { AutoFocusModule } from 'primeng/autofocus';

@Component({
  selector: 'inplace-input',
  imports: [
    InplaceModule,
    FormsModule,
    ButtonModule,
    InputTextModule,
    ButtonGroupModule,
    CheckIcon,
    TimesIcon,
    AutoFocusModule,
  ],
  templateUrl: './inplace-input.component.html',
  styleUrl: './inplace-input.component.scss',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class InplaceInputComponent {
  inplaceRef = viewChild.required<Inplace>(Inplace);

  value = model<string>('');

  placeholder = input<string>('');
  actionButtons = input<boolean>(false);
  isActive = input<boolean>(false);

  saved = output<string>();
  closed = output<void>();

  tempValue = signal('');

  displayValue = computed(() => this.value() || this.placeholder());

  onActivate(): void {
    this.tempValue.set(this.value());
  }

  onDeactivate(): void {
    // Clean up if needed
  }

  updateTempValue(newValue: string): void {
    this.tempValue.set(newValue);
  }

  save(): void {
    this.value.set(this.tempValue());
    this.saved.emit(this.tempValue());
    this.inplaceRef().deactivate();
  }

  close(): void {
    this.tempValue.set(this.value());
    this.inplaceRef().deactivate();
    this.closed.emit();
  }
}
