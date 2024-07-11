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
  private name: string = 'world'
  date='2024-07-11'
  fontSize = 24
  list = [
    { id: 1, name: 'Madrid'},
    { id: 2, name: 'BARCELONA'},
    { id: 3, name: 'oviedo'},
    { id: 4, name: 'ciudad Real'},
  ]
  idProvince = 2

  result?: string
  visible = true
  esthetic = { important: true, error: false, urgent: true }
Component: any;

  constructor(public vm: NotificationService) { }

  public get Name(): string { return this.name }
  public set Name(value: string) {
    if(this.name === value) return
    this.name = value
  }

  public sayHi(): void {
    this.result = `Hello ${this.Name}`
  }

  public sayBye(): void {
    this.result = `Goodbye ${this.Name}`
  }

  public say(something: string): void {
    this.result = `Say ${something}`
  }

  change() {
    this.visible = !this.visible
    this.esthetic.error = !this.esthetic.error
    this.esthetic.important = !this.esthetic.important
  }

  public calculate(a: number, b: number): number { return a + b }

  public add(province: string) {
    const id = this.list[this.list.length - 1].id + 1
    this.list.push({id, name: province})
    this.idProvince = id
  }

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