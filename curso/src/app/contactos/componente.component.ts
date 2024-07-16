/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-empty-function */
/* eslint-disable @angular-eslint/no-empty-lifecycle-method */
import { Component, OnInit, OnDestroy, Input, OnChanges, SimpleChanges, forwardRef } from '@angular/core';
import { ActivatedRoute, Router, ParamMap, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatePipe, NgIf, } from '@angular/common';
import { PaginatorModule } from 'primeng/paginator';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { ContactosViewModelService } from './servicios.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-contactos',
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrls: ['./componente.component.css'],
  // providers: [ContactosViewModelService]
  standalone: true,
  imports: [
    forwardRef(() => ContactosAddComponent),
    forwardRef(() => ContactosEditComponent),
    forwardRef(() => ContactosViewComponent),
    forwardRef(() => ContactosListComponent),
  ],
})
export class ContactosComponent implements OnInit, OnDestroy {
  constructor(protected vm: ContactosViewModelService) { }
  public get VM(): ContactosViewModelService { return this.vm; }
  ngOnInit(): void {
    // this.vm.list();
    this.vm.load()
  }
  ngOnDestroy(): void { this.vm.clear(); }
}


@Component({
  selector: 'app-contactos-list',
  templateUrl: './tmpl-list.sin-rutas.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [PaginatorModule, NgIf, ]
})
export class ContactosListComponent implements OnInit, OnDestroy {
  constructor(protected vm: ContactosViewModelService) { }
  public get VM(): ContactosViewModelService { return this.vm; }
  ngOnInit(): void { }
  ngOnDestroy(): void { }
}
@Component({
  selector: 'app-contactos-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator, ErrorMessagePipe]
})
export class ContactosAddComponent implements OnInit {
  constructor(protected vm: ContactosViewModelService) { }
  public get VM(): ContactosViewModelService { return this.vm; }
  ngOnInit(): void { }
}
@Component({
  selector: 'app-contactos-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator, ErrorMessagePipe]
})
export class ContactosEditComponent implements OnInit, OnDestroy {
  constructor(protected vm: ContactosViewModelService) { }
  public get VM(): ContactosViewModelService { return this.vm; }
  ngOnInit(): void { }
  ngOnDestroy(): void { }
}
@Component({
  selector: 'app-contactos-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./componente.component.css'],
  standalone: true,
  imports: [DatePipe]
})
export class ContactosViewComponent implements OnInit, OnDestroy {
  constructor(protected vm: ContactosViewModelService) { }
  public get VM(): ContactosViewModelService { return this.vm; }
  ngOnInit(): void { }
  ngOnDestroy(): void { }
}

export const CONTACTOS_COMPONENTES = [
  // ContactosComponent,
  ContactosListComponent, ContactosAddComponent, ContactosEditComponent, ContactosViewComponent,
];