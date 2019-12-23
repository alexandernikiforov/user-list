import {Component, OnInit} from '@angular/core';
import {environment} from '../environments/environment';
import {Router} from '@angular/router';
import {LoginService} from './login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private router: Router, private loginService: LoginService) {
    console.log('Environment: ' + environment.apiUrl);
  }

  ngOnInit(): void {
    this.loginService.checkLogin();
  }
}
