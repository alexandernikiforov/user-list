import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EntryScreenComponent} from "./entry-screen/entry-screen.component";
import {LoginGuard} from "./login-guard";
import {AdminComponent} from "./admin/admin.component";

const routes: Routes = [
  {
    path: '', component: EntryScreenComponent
  },
  {
    path: 'admin', component: AdminComponent, canActivate: [LoginGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
