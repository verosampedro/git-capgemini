import { Routes } from '@angular/router';
import { ActorsComponent, FilmsComponent, HomeComponent } from './components';

export const routes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: '', pathMatch: 'full', component: ActorsComponent },
    { path: 'actores/v2', component: ActorsComponent },
    { path: 'peliculas/v1', component: FilmsComponent },
];