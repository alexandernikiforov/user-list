import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {LoginService} from './login.service';

@Injectable()
export class LoginGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {
  }

  canActivate(): boolean {
    const principal = this.loginService.getUser();

    if (principal.roles.indexOf('admin') > -1) {
      return true;
    } else {
      this.router.navigate(['/']).then(
        x => console.log('Redirected back to the home page')
      );
      return false;
    }
  }
}
