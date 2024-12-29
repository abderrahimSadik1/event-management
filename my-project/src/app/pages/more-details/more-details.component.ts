import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import {
  HttpClient,
  HttpClientModule,
  HttpHeaders,
} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-more-details',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule, HttpClientModule],
  templateUrl: './more-details.component.html',
  styleUrls: ['./more-details.component.css'],
})
export class MoreDetailsComponent implements OnInit {
  eventDetails: any = {}; // Holds event details fetched from API
  comments: any[] = []; // Holds comments fetched from API
  newComment: string = ''; // Holds the new comment input
  token = localStorage.getItem('authToken'); // Fetch auth token from local storage
  headers = new HttpHeaders({
    Authorization: `Bearer ${this.token}`, // Include the token in the Authorization header
  });
  isJoined: boolean = false; // Track if the user has joined the event
  userId: string | null = null; // Store the current user's ID

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    const eventId = this.route.snapshot.paramMap.get('id');
    if (eventId) {
      this.fetchEventDetails(eventId);
      this.fetchComments(eventId);
      this.fetchUserId(); // Fetch the user ID when the component initializes
    }
  }

  // Fetch the current user's ID from the backend
  fetchUserId(): void {
    this.http
      .get(`http://localhost:8088/api/utilisateurs/id`, {
        headers: this.headers,
      })
      .subscribe(
        (data: any) => {
          this.userId = data; // Store the user ID
          console.log('User ID:', this.userId); // For debugging
        },
        (error) => console.error('Error fetching user ID:', error)
      );
  }

  // Fetch event details, including the list of participants
  fetchEventDetails(eventId: string): void {
    this.http
      .get(`http://localhost:8088/api/evenements/${eventId}`, {
        headers: this.headers,
      })
      .subscribe(
        (data: any) => {
          this.eventDetails = data;
          console.log('Participants:', data.participantIds);
          // Check if participants is an array and check if userId exists in it
          if (Array.isArray(data.participantIds)) {
            this.isJoined = data.participantIds.includes(this.userId);
          } else {
            // If participants is undefined or not an array, assume user is not joined
            this.isJoined = false;
          }
        },
        (error) => console.error('Error fetching event details:', error)
      );
  }

  // Fetch event comments
  fetchComments(eventId: string): void {
    this.http
      .get(`http://localhost:8088/api/commentaires/evenement/${eventId}`, {
        headers: this.headers,
      })
      .subscribe(
        (data: any) => {
          this.comments = data;
        },
        (error) => console.error('Error fetching comments:', error)
      );
  }

  // Submit a new comment
  submitComment(): void {
    if (this.newComment.trim()) {
      const commentData = {
        contenu: this.newComment,
        evenement: {
          id: this.eventDetails.id,
        },
      };
      this.http
        .post(`http://localhost:8088/api/commentaires`, commentData, {
          headers: this.headers,
        })
        .subscribe(
          (response: any) => {
            this.comments.push(response);
            this.newComment = '';
          },
          (error) => console.error('Error submitting comment:', error)
        );
    }
  }

  // Join the event
  joinEvent(): void {
    this.isJoined = true;
    if (this.userId) {
      this.http
        .post(
          `http://localhost:8088/api/evenements/${this.eventDetails.id}/join`,
          {},
          {
            headers: this.headers,
          }
        )
        .subscribe(
          () => {
            alert('You have successfully joined the event!');
          },
          (error) => console.error('Error joining the event:', error)
        );
    }
  }
}
