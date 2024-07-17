import { NgModule } from '@angular/core';
import { Peliculas_COMPONENTES, PeliculasAddComponent, PeliculasEditComponent, PeliculasListComponent, PeliculasViewComponent } from './componente.component';
import { RouterModule, Routes } from '@angular/router';


export const routes: Routes = [
  { path: '', component: PeliculasListComponent },
  { path: 'add', component: PeliculasAddComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: ':id/edit', component: PeliculasEditComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: ':id', component: PeliculasViewComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: ':id/:kk', component: PeliculasViewComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
]
@NgModule({
  declarations: [],
  imports: [ Peliculas_COMPONENTES, RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class PeliculasModule { }