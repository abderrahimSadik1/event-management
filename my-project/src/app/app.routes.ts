import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { EventsComponent } from './pages/events/events.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AboutComponent } from './pages/about/about.component';
import { LearnMoreComponent } from './pages/learn-more/learn-more.component';
import { CollaborationsComponent } from './pages/collaborations/collaborations.component';
import { MoreDetailsComponent } from './pages/more-details/more-details.component';
import { CreateEventComponent } from './pages/create-event/create-event.component';
export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component:LoginComponent },
    {path: 'register', component: RegisterComponent },
    {path: 'home', component: HomeComponent },
    {path: 'events', component: EventsComponent },
    {path: 'collaboration', component: CollaborationsComponent },
    {path: 'about', component: AboutComponent },
    {path: 'learnmore', component: LearnMoreComponent },
    {path: 'more', component: MoreDetailsComponent },
    {path: 'createEvent', component:CreateEventComponent },
    {path: 'profile', component: ProfileComponent }

];
