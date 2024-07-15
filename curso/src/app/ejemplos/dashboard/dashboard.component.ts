import { Component } from '@angular/core';
import { HomeComponent } from 'src/app/main';
import GraficoSvgComponent from 'src/lib/my-core/components/grafico-svg/grafico-svg.component';
import { NotificationComponent } from "../../main/notification/notification.component";
import { CommonModule } from '@angular/common';
import { CalculadoraComponent } from '../calculadora/calculadora.component';
import { FormularioComponent } from '../formulario/formulario.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NotificationComponent, CommonModule, ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  menu = [
    { texto: 'HOME', icono: 'fa-solid fa-house', componente: HomeComponent },
    { texto: 'FORM', icono: 'fa-solid fa-person-chalkboard', componente: FormularioComponent},
    { texto: 'CALCULATOR', icono: 'fa-solid fa-calculator', componente: CalculadoraComponent },
    { texto: 'ANIMATION', icono: 'fa-solid fa-image', componente: GraficoSvgComponent },
  ]
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  actual: any = this.menu[0].componente

  seleccionar(indice: number) {
    this.actual = this.menu[indice].componente
  }
}
