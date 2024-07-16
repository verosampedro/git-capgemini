import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AjaxWaitComponent, NotificationComponent, NotificationModalComponent } from './main';
import { NavigationService } from './common-services';
import { HeaderComponent } from './main/header/header.component';
import { DashboardComponent } from "./ejemplos/dashboard/dashboard.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NotificationComponent, NotificationModalComponent, AjaxWaitComponent, HeaderComponent, DashboardComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  // eslint-disable-next-line @typescript-eslint/no-unused-vars, @typescript-eslint/no-empty-function
  constructor(nav: NavigationService) { }
}