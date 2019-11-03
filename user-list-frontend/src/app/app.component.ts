import {Component} from '@angular/core';
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'user-list-frontend';

  constructor() {
    console.log('Environment: ' + environment.apiUrl);
  }
}
