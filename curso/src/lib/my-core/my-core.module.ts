import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PIPES_CADENAS } from './pipes/cadenas.pipes';



@NgModule({
  declarations: [ ],
  exports: [ PIPES_CADENAS ],
  imports: [
    CommonModule, PIPES_CADENAS
  ]
})
export class MyCoreModule { }