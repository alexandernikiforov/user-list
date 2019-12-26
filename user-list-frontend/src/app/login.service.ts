import {Inject, Injectable} from '@angular/core';
import {AuthenticationParameters, AuthResponse, UserAgentApplication} from 'msal';
import {AuthError} from 'msal/src/error/AuthError';
import {StringDict} from 'msal/lib-es6/MsalTypes';
import {from, Subscription, timer} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {ENVIRONMENT_CONFIG, EnvironmentConfig} from './core/services/app-init.service';

const authRequest: AuthenticationParameters = {
  scopes: [
    'api://17d8d012-4b5d-456e-bc55-10e2c66f60ba/User.Read.All',
    'api://17d8d012-4b5d-456e-bc55-10e2c66f60ba/User.ReadWrite.All'
  ]
};

const loginRequest: AuthenticationParameters = {
  scopes: ['openid', 'email']
};

export interface User {
  readonly loggedIn: boolean;
  readonly name?: string;
  readonly email?: string;
  roles: ReadonlyArray<string>;
}

const UnknownPrincipal: User = {
  loggedIn: false,
  roles: []
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private userAgentApplication: UserAgentApplication;

  constructor(@Inject(ENVIRONMENT_CONFIG) private environmentConfig: EnvironmentConfig) {
    this.userAgentApplication = new UserAgentApplication({
      auth: {
        clientId: this.environmentConfig.clientId,
        redirectUri: this.environmentConfig.redirectUri,
        authority: `https://login.microsoftonline.com/${this.environmentConfig.tenantId}`
      }
    });
  }

  private users: Subscription;
  private user: User = UnknownPrincipal;

  get loggedIn(): boolean {
    return this.user.loggedIn;
  }

  private static interactionRequired(error: AuthError): boolean {
    return (error.errorCode === 'consent_required'
      || error.errorCode === 'interaction_required'
      || error.errorCode === 'login_required');
  }

  private static toPrincipal(claims: StringDict): User {
    const roles: Array<string> = claims.roles as unknown as Array<string>;

    return {
      loggedIn: true,
      name: claims.name,
      email: claims.email,
      roles: roles || []
    };
  }

  public getUser(): User {
    return this.user;
  }

  public checkLogin() {
    const account = this.userAgentApplication.getAccount();
    if (account) {
      this.user = LoginService.toPrincipal(account.idToken);
      this.subscribeToIdTokenRenewal();
    }
  }

  public login() {
    this.userAgentApplication.loginPopup(loginRequest)
      .then(loginResponse => {
        console.log(`successful login: ${loginResponse.idToken.claims.toString()}`);

        this.user = LoginService.toPrincipal(loginResponse.idToken.claims);
        this.subscribeToIdTokenRenewal();
        // get the token immediately to cache in MSAL it for subsequent requests
        return this.getAuthResponse();
      })
      .then(() => {
        console.log('access token acquired');
      })
      .catch((authError: AuthError) => {
        console.log(`Login failure: ${authError}`);
      });
  }

  public getAccessToken(): Promise<string> {
    return this.getAuthResponse().then(authResponse => {
      console.log('access token acquired');
      return authResponse.accessToken;
    });
  }

  public logout() {
    this.users.unsubscribe();
    this.user = UnknownPrincipal;
    this.userAgentApplication.logout();
  }

  private getAuthResponse(): Promise<AuthResponse> {
    return this.userAgentApplication.acquireTokenSilent(authRequest)
      .catch((authError: AuthError) => {
        console.log(authError);
        if (LoginService.interactionRequired(authError)) {
          return this.userAgentApplication.acquireTokenPopup(authRequest);
        } else {
          throw authError;
        }
      });
  }

  private subscribeToIdTokenRenewal() {
    this.users = timer(0, 5000)
      .pipe(
        switchMap(() => from(this.userAgentApplication.acquireTokenSilent(loginRequest))),
        map(loginResponse => LoginService.toPrincipal(loginResponse.idToken.claims))
      )
      .subscribe(user => this.user = user, () => this.user = UnknownPrincipal);
  }
}
