import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "../login.service";

@Component({
  selector: 'app-entry-screen',
  templateUrl: './entry-screen.component.html',
  styleUrls: ['./entry-screen.component.css']
})
export class EntryScreenComponent implements OnInit {

  constructor(private router: Router, private loginService: LoginService) {
  }

  ngOnInit() {
  }

  navigateToAdminPage() {
    this.router.navigate(["/admin"])
      .then(result => {
        if (result) {
          this.loginService.loggedIn = true;
        }
      })
  }
}
