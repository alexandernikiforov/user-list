import {Component} from '@angular/core';
import {environment} from '../environments/environment';
import {Router} from "@angular/router";
import {LoginService} from "./login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private router: Router, private loginService: LoginService) {
    console.log('Environment: ' + environment.apiUrl);
  }

  logout() {
    this.router.navigate(["/"])
      .then(result => {
        if (result) {
          this.loginService.loggedIn = false;
        }
      });
  }

  isLoggedIn(): boolean {
    return this.loginService.loggedIn;
  }
}
