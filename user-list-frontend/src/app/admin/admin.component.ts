import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

interface Admin {
  name: string,
  email: string
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  admin: Admin;

  constructor(private http: HttpClient) {
    this.http.get<Admin>("/api/admin")
      .subscribe(result => this.admin = result);
  }

  ngOnInit() {
  }

}
