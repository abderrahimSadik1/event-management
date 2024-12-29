import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import {
  HttpClient,
  HttpClientModule,
  HttpHeaders,
} from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-events',
  standalone: true,
  imports: [
    NavbarComponent,
    FooterComponent,
    CommonModule,
    RouterModule,
    HttpClientModule,
    FormsModule,
  ],
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css'],
})
export class EventsComponent implements OnInit {
  events: any[] = [];
  filteredEvents: any[] = []; // Store filtered events
  searchTerm: string = ''; // Store the search term
  token = localStorage.getItem('authToken');
  headers = new HttpHeaders({
    Authorization: `Bearer ${this.token}`, // Include the token in the Authorization header
  });

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Fetch events
    this.http
      .get('http://localhost:8088/api/evenements', { headers: this.headers })
      .subscribe(
        (data: any) => {
          this.events = data;
          this.filteredEvents = data; // Initially, all events are displayed

          // Fetch organizer's details for each event
          this.events.forEach((event) => {
            if (event.createurId) {
              this.fetchOrganizerDetails(event.createurId, event);
            }
          });
        },
        (error) => console.error('There was an error fetching events!', error)
      );
  }

  // Fetch organizer details using the createurId
  fetchOrganizerDetails(createurId: number, event: any): void {
    this.http
      .get(`http://localhost:8088/api/utilisateurs/${createurId}`, {
        headers: this.headers,
      })
      .subscribe(
        (userData: any) => {
          // Add organizer's full name to the event data
          event.organizerName = `${userData.nom} ${userData.prenom}`;
        },
        (error) => console.error('Error fetching organizer details:', error)
      );
  }

  // Filter events based on the search term
  filterEvents(): void {
    if (this.searchTerm.trim()) {
      this.filteredEvents = this.events.filter(
        (event) =>
          event.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
          event.lieu.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
          event.description
            .toLowerCase()
            .includes(this.searchTerm.toLowerCase())
      );
    } else {
      this.filteredEvents = this.events; // Show all events if search term is empty
    }
  }
}
