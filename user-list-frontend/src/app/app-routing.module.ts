import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from './user-list/user-list.component';
import {EntryScreenComponent} from "./entry-screen/entry-screen.component";
import {LoginGuard} from "./login-guard";

const routes: Routes = [
  {
    path: '', component: EntryScreenComponent
  },
  {
    path: 'admin', component: UserListComponent, canActivate: [LoginGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
