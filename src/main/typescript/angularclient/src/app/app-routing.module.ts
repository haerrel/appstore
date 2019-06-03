import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './routes/root/home/home.component';
import {LoginComponent} from './routes/login/login.component';
import {RootComponent} from './routes/root/root.component';
import {NewAppComponent} from './routes/root/new-app/new-app.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {path: 'apps', component: RootComponent,
  children: [
    {path: '', component: HomeComponent},
    { path: 'new', component: NewAppComponent}, //TODO not here
  ]},
  { path: 'login', component: LoginComponent},
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
