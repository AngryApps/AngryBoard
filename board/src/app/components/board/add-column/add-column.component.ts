import {
  ChangeDetectionStrategy,
  Component,
  inject,
  viewChild,
} from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { Popover, PopoverModule } from 'primeng/popover';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { TextareaModule } from 'primeng/textarea';
import { MessageModule } from 'primeng/message';
import { BoardService } from '../../../shared';

@Component({
  selector: 'add-column',
  standalone: true,
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
  public popover = viewChild.required<Popover>('op');

  private boardService = inject(BoardService);
  private _fb = inject(FormBuilder);

  protected columnForm = this._fb.nonNullable.group({
    title: ['', Validators.required],
    description: [''],
  });

  protected addColumn() {
    if (this.columnForm.invalid) return;

    const title = this.columnForm.value.title;
    const description = this.columnForm.value.description;

    if (!title) return;

    this.boardService.addColumn(title, description);
    this.columnForm.reset();
    this.popover().hide();
  }
}
