import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './routes/root/home/home.component';
import {LoginComponent} from './routes/login/login.component';
import {RootComponent} from './routes/root/root.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {path: 'apps', component: RootComponent,
  children: [
    {path: '', component: HomeComponent}
  ]},
  { path: 'login', component: LoginComponent},
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
