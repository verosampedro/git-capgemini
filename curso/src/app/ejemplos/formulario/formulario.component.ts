import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-formulario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './formulario.component.html',
  styleUrl: './formulario.component.css'
})
export class FormularioComponent {
  modo: 'add' | 'edit' = 'add'
  elemento: any = { id: 0, name: 'Verónica', surname: 'Sampedro', email: 'verosampedro@icloud.com', age: 22, date: '15-07-2024', troubled: false}

  add() {
    this.elemento = {}
    this.modo = 'add'
  }

  edit(key: number) {
    this.elemento = { id: key, name: 'Verónica', surname: 'Sampedro', email: 'verosampedro@icloud.com', age: 22, date: '15/07/2024', troubled: false}
    this.modo = 'edit'
  }

  cancel() {

  }

  send() {
    switch(this.modo) {
      case 'add':
        window.alert(`POST: ${JSON.stringify(this.elemento)}`)
        this.cancel()
        break
      case 'edit':
        window.alert(`PUT: ${JSON.stringify(this.elemento)}`)
        this.cancel()
        break
    }
  }
}
