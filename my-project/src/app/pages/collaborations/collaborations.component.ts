import { Component } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterModule, Router } from '@angular/router';
import {
  HttpClient,
  HttpHeaders,
  HttpClientModule,
} from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-collaborations',
  standalone: true,
  imports: [
    NavbarComponent,
    FooterComponent,
    RouterModule,
    HttpClientModule,
    FormsModule,
  ],
  templateUrl: './collaborations.component.html',
  styleUrl: './collaborations.component.css',
})
export class CollaborationsComponent {
  evenement: EvenementDTO = {
    nom: '',
    date: '',
    lieu: '',
    description: '',
  };
  image: File | null = null;

  constructor(private http: HttpClient, private router: Router) {}

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.image = file;
    }
  }

  onSubmit(): void {
    const formData = new FormData();
    formData.append(
      'evenement',
      new Blob([JSON.stringify(this.evenement)], { type: 'application/json' })
    );
    if (this.image) {
      formData.append('image', this.image, this.image.name);
    }

    // Retrieve token from localStorage
    const token = localStorage.getItem('authToken'); // Adjust key as per your storage setup
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Include the token in the Authorization header
    });

    this.http
      .post('http://localhost:8088/api/evenements', formData, { headers })
      .subscribe(
        (response) => {
          console.log('Event created successfully', response);
          this.router.navigate(['/']);
        },
        (error) => {
          console.error('There was an error!', error);
        }
      );
  }
}

interface EvenementDTO {
  nom: string;
  date: string;
  lieu: string;
  description: string;
}
