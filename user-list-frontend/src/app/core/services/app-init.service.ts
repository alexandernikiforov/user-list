import {APP_INITIALIZER, Injectable, InjectionToken, Provider} from '@angular/core';

export interface EnvironmentConfig {
  // redirect URI for login
  redirectUri: string;

  // the ID of the tenant (the Active Directory)
  tenantId: string;

  // the ID of the client (the app registered in the Active Directory)
  clientId: string;
}

@Injectable()
export class AppInitService {
  private config: EnvironmentConfig;

  constructor() {
  }

  get environmentConfig(): EnvironmentConfig {
    return this.config;
  }

  init(config: EnvironmentConfig): void {
    console.log(`init called: ${config}`);
    this.config = config;
  }
}

export function initRuntimeEnvironment(appInitService: AppInitService): () => Promise<any> {
  return () => fetch('/conf/environment.json', {
    cache: 'no-cache',
  })
    .then(
      result => {
        if (!result.ok) {
          throw new Error(result.statusText);
        }
        console.log(`loading the environment config: ${result}`);
        return result.json();
      }
    )
    .then(responseAsJson => {
      appInitService.init(responseAsJson as EnvironmentConfig);
    })
    .catch(error => {
      console.error(`cannot load environment config: ${error}`);
    });
}

export const ENVIRONMENT_CONFIG = new InjectionToken<EnvironmentConfig>('token.EnvironmentConfig');

export function getEnvironmentConfig(appInitService: AppInitService): EnvironmentConfig {
  return appInitService.environmentConfig;
}

export const ENVIRONMENT_INITIALIZERS: Array<Provider> = [
  AppInitService,
  {provide: APP_INITIALIZER, useFactory: initRuntimeEnvironment, deps: [AppInitService], multi: true},
  {provide: ENVIRONMENT_CONFIG, useFactory: getEnvironmentConfig, deps: [AppInitService]}
];
