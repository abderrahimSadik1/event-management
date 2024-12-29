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
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  user = {
    email: '',
    motDePasse: '',
  };

  private apiUrl = 'http://localhost:8080/login'; // Your API URL for login

  constructor(private http: HttpClient, private router: Router) {}
  headers = new Headers({ 'Content-Type': 'application/json' });
  onSubmit() {
    console.log('Utilisateur:', this.user, this.headers);

    this.http
      .post<{ token: string }>('http://localhost:8088/login', this.user)
      .subscribe(
        (response) => {
          console.log('Login successful', response);
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
            console.error('Login failed', error);
          }
        }
      );
  }
}
