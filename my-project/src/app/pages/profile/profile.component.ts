import { Component, OnInit } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpClientModule,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavbarComponent, FooterComponent, HttpClientModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  user: any = {}; // Object to hold user data
  organizedEvents: any[] = []; // Store events organized by the user
  authToken: string | null = localStorage.getItem('authToken'); // Get authToken from localStorage

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    if (this.authToken) {
      this.getUserData();
      this.getOrganizedEvents(); // Fetch events organized by the user
    } else {
      this.router.navigate(['/login']); // Redirect to login if no authToken
    }
  }

  // Fetch user data
  getUserData(): void {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authToken}`, // Include the token in the headers
    });

    this.http
      .get('http://localhost:8088/api/utilisateurs', { headers })
      .subscribe(
        (response: any) => {
          this.user = response; // Update the user data with the response
        },
        (error) => {
          console.error('Error fetching user data:', error);
        }
      );
  }

  // Fetch events organized by the user
  getOrganizedEvents(): void {
    const userId = this.user.id; // Use user ID to fetch the events
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.authToken}`, // Include the token in the headers
    });

    this.http
      .get('http://localhost:8088/api/evenements/user', {
        headers,
      })
      .subscribe(
        (response: any) => {
          this.organizedEvents = response; // Store events organized by the user
        },
        (error) => {
          console.error('Error fetching organized events:', error);
        }
      );
  }

  saveChanges(): void {
    // Logic to send the updated user data to the backend if needed
  }
}
