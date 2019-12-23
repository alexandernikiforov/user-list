import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginService, User} from '../login.service';

interface Admin {
  name: string;
  email: string;
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  user: User;

  admin: Admin;

  constructor(private http: HttpClient, private loginService: LoginService) {
  }

  ngOnInit() {
    this.user = this.loginService.getUser();
    this.loginService.getAccessToken()
      .then(accessToken => {
        this.http
          .get<Admin>('/api/admin', {
            headers: {
              Authorization: `Bearer ${accessToken}`
            }
          })
          .subscribe(result => this.admin = result);
      });
  }

}
