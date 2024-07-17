import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PIPES_CADENAS } from './pipes/cadenas.pipe';
import { PIPES_NUMERICOS } from './pipes/numericos.pipe';



@NgModule({
  declarations: [ ],
  imports: [
    CommonModule, PIPES_CADENAS, PIPES_NUMERICOS,
  ],
  exports: [ PIPES_CADENAS, PIPES_NUMERICOS, ],
})
export class MyCoreModule { }
