import { Injectable, OnDestroy } from '@angular/core';
import { LoggerService } from '@my/core';
import { Subject } from 'rxjs';

export enum NotificationType {
  error = 'error',
  warn = 'warn',
  info = 'info',
  log = 'log'
}

export class Notification {
  constructor(private id: number, private message: string,
    private type: NotificationType) { }
  public get Id() { return this.id; }
  public get Message() { return this.message; }
  public get Type() { return this.type; }
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService implements OnDestroy  {
  public readonly NotificationType = NotificationType;
  private list: Notification[] = [];
  private notification = new Subject<Notification>();

  constructor(private out: LoggerService) { }

  public get List(): Notification[] { return Object.assign([], this.list); }
  public get Notifications() { return this.list.length > 0; }
  public get Notification() { return this.notification; }

  public add(msg: string, type: NotificationType = NotificationType.error) {
    if (!msg || msg === '') {
      this.out.error('Missing notification message.');
      return;
    }
    const id = this.Notifications ?
      (this.list[this.list.length - 1].Id + 1) : 1;
    const n = new Notification(id, msg, type);
    this.list.push(n);
    this.notification.next(n);
    if (type === NotificationType.error) {
      this.out.error(`NOTIFICATION: ${msg}`);
    }
  }
  public remove(index: number) {
    if (index < 0 || index >= this.list.length) {
      this.out.error('Index out of range.');
      return;
    }
    this.list.splice(index, 1);
  }
  public clear() {
    if (this.Notifications)
      this.list.splice(0);
  }

  ngOnDestroy(): void {
    this.notification.complete()
  }
}