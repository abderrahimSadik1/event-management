import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-event',
  standalone: true,
  imports: [RouterModule,NavbarComponent,FooterComponent,ReactiveFormsModule,CommonModule ],
  templateUrl: './create-event.component.html',
  styleUrl: './create-event.component.css'
})
export class CreateEventComponent {
  eventForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.eventForm = this.fb.group({
      eventName: ['', [Validators.required, Validators.minLength(3)]],
      eventImage: [null, Validators.required],
      eventDate: ['', Validators.required],
      eventLocation: ['', [Validators.required, Validators.minLength(5)]],
    });
  }

  ngOnInit(): void {}

  onFileChange(event: any): void {
    const file = event.target.files[0];
    this.eventForm.patchValue({
      eventImage: file,
    });
    this.eventForm.get('eventImage')?.updateValueAndValidity();
  }

  createEvent(): void {
    if (this.eventForm.valid) {
      const formData = new FormData();
      formData.append('eventName', this.eventForm.get('eventName')?.value);
      formData.append('eventImage', this.eventForm.get('eventImage')?.value);
      formData.append('eventDate', this.eventForm.get('eventDate')?.value);
      formData.append('eventLocation', this.eventForm.get('eventLocation')?.value);

      console.log('Event Data:', formData);
      // Replace with service call to send data to backend
    } else {
      console.error('Form is invalid');
    }
  }
}
