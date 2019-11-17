import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserListComponent} from './user-list/user-list.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTableModule} from "@angular/material/table";
import {EntryScreenComponent} from './entry-screen/entry-screen.component';
import {MatButtonModule} from "@angular/material/button";
import {LoginGuard} from "./login-guard";

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    EntryScreenComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatTableModule,
    MatButtonModule
  ],
  providers: [LoginGuard],
  bootstrap: [AppComponent]
})
export class AppModule {
}
