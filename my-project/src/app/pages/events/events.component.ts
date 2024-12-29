import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { Event } from '../../interfaces/event';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import {
  HttpClient,
  HttpClientModule,
  HttpHeaders,
} from '@angular/common/http';

@Component({
  selector: 'app-events',
  standalone: true,
  imports: [
    NavbarComponent,
    FooterComponent,
    CommonModule,
    RouterModule,
    HttpClientModule,
  ],
  templateUrl: './events.component.html',
  styleUrl: './events.component.css',
})
export class EventsComponent implements OnInit {
  events: any[] = [];

  constructor(private http: HttpClient) {}
  token = localStorage.getItem('authToken');
  headers = new HttpHeaders({
    Authorization: `Bearer ${this.token}`, // Include the token in the Authorization header
  });
  ngOnInit(): void {
    // Replace with the actual API URL
    console.log('token', this.token);
    this.http
      .get('http://localhost:8088/api/evenements', { headers: this.headers })
      .subscribe(
        (data: any) => {
          this.events = data;
          console.log(data);
        },
        (error) => console.error('There was an error!', error)
      );
  }
}
