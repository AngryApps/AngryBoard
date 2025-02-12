import {
  ChangeDetectionStrategy,
  Component,
  inject,
  viewChild,
} from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { Popover, PopoverModule } from 'primeng/popover';
import {
  FormBuilder,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { TextareaModule } from 'primeng/textarea';
import { ColumnService } from '../services';
import { MessageModule } from 'primeng/message';

@Component({
  selector: 'add-column',
  imports: [
    ButtonModule,
    PopoverModule,
    InputTextModule,
    FloatLabelModule,
    TextareaModule,
    ReactiveFormsModule,
    MessageModule,
  ],
  templateUrl: './add-column.component.html',
  styleUrl: './add-column.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AddColumnComponent {
  popover = viewChild.required<Popover>('op');

  columnService = inject(ColumnService);
  private _fb = inject(FormBuilder);

  columnForm = this._fb.group({
    title: new FormControl('', Validators.required),
    description: new FormControl(''),
  });

  isFieldInvalid(fieldName: string) {
    const field = this.columnForm.get(fieldName);
    return field ? field.invalid && (field.dirty || field.touched) : false;
  }

  protected addColumn() {
    if (this.columnForm.invalid) return;

    const title = this.columnForm.value.title;
    const description = this.columnForm.value.description;

    if (!title) return;

    this.columnService.addColumn(title, description);
    this.columnForm.reset();
    this.popover().hide();
  }
}
