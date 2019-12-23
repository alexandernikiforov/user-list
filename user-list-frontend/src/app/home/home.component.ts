import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService, User} from '../login.service';

@Component({
  selector: 'app-entry-screen',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(private router: Router, public loginService: LoginService) {
  }

  get user(): User {
    return this.loginService.getUser();
  }

  get loggedIn(): boolean {
    return this.loginService.loggedIn;
  }

  ngOnInit() {
  }

  navigateToAdminPage() {
    this.router.navigate(['/admin'])
      .then(() => {
        console.log('Navigated to the admin page');
      });
  }
}
