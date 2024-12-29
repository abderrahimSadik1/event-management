import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {
  HttpClient,
  HttpHeaders,
  HttpClientModule,
} from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterModule, FormsModule, HttpClientModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  utilisateur = {
    nom: '',
    prenom: '',
    telephone: null,
    email: '',
    motDePasse: '',
  };

  private apiUrl = 'http://localhost:8080/register'; // Your API URL

  constructor(private http: HttpClient, private router: Router) {}
  headers = new Headers({ 'Content-Type': 'application/json' });

  onSubmit() {
    console.log('Utilisateur:', this.utilisateur, this.headers);

    this.http
      .post<{ token: string }>(
        'http://localhost:8088/register',
        this.utilisateur
      )
      .subscribe(
        (response) => {
          console.log('Registration successful', response);
          if (response.token) {
            localStorage.setItem('authToken', response.token);
          }
          this.router.navigate(['/home']);
        },
        (error) => {
          if (error.status === 0) {
            console.error(
              'Network error - please check your internet connection or CORS settings',
              error
            );
          } else {
            console.error('Registration failed', error);
          }
        }
      );
  }
}
