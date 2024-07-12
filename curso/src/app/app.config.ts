import { registerLocaleData } from '@angular/common';
import localeEs from '@angular/common/locales/es';
import localeEsExtra from '@angular/common/locales/extra/es';
registerLocaleData(localeEs, 'es', localeEsExtra);

import { ApplicationConfig, provideZoneChangeDetection, LOCALE_ID } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { ERROR_LEVEL, LoggerService } from '@my/core';
import { environment } from 'src/environments/environment';

export const appConfig: ApplicationConfig = {
  providers: [
    LoggerService,
    {provide: ERROR_LEVEL, useValue: environment.ERROR_LEVEL},
    {provide: LOCALE_ID, useValue: 'es-ES'},
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
  ]
};