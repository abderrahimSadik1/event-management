import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { EventsComponent } from '../events/events.component';
@Component({
  selector: 'app-more-details',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './more-details.component.html',
  styleUrls: ['./more-details.component.css'], 
})
export class MoreDetailsComponent implements OnInit {
  eventDetails = {
    title: 'the Event Name ..',
    description: 'A detailed description of this amazing event happening soon.',
    imageUrl: 'https://via.placeholder.com/600x400', 
  };

  comments = [
    {
      username: 'Alice',
      content: 'This event is going to be amazing! Can’t wait!',
      createdAt: '2024-11-29',
    },
    {
      username: 'Bob',
      content: 'I really hope to attend this one. The lineup is great!',
      createdAt: '2024-11-28',
    },
    {
      username: 'Charlie',
      content: 'Looks awesome. I’ll bring my friends for sure!',
      createdAt: '2024-11-27',
    },
  ];

  newComment: string = '';

  constructor() {}

  ngOnInit(): void {}

  submitComment() {
    if (this.newComment.trim()) {
      this.comments.push({
        username: 'CurrentUser', 
        content: this.newComment,
        createdAt: new Date().toISOString().split('T')[0], 
      });
      this.newComment = ''; 
    }
  }
}
