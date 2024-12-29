import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FooterComponent } from "../../components/footer/footer.component";
@Component({
  selector: 'app-about',
  standalone: true,
  imports: [RouterModule, FooterComponent],
  templateUrl: './about.component.html',
  styleUrl: './about.component.css'
})
export class AboutComponent {

}
