import { Component, OnDestroy, OnInit } from '@angular/core';
import { Unsubscribable } from 'rxjs';
import { NotificationService, NotificationType } from 'src/app/common-services';

@Component({
  selector: 'app-demos',
  standalone: true,
  imports: [],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css'
})
export class DemosComponent implements OnInit, OnDestroy  {
  constructor(public vm: NotificationService) { }

  private subscriber?: Unsubscribable;

  ngOnInit(): void {
    this.subscriber = this.vm.Notification.subscribe(n => {
    });
  }
  ngOnDestroy(): void {
    if (this.subscriber) {
      this.subscriber.unsubscribe();
    }
  }

}