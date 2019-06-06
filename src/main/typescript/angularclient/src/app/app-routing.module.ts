import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './routes/root/home/home.component';
import {LoginComponent} from './routes/login/login.component';
import {RootComponent} from './routes/root/root.component';
import {NewAppComponent} from './routes/root/user/new-app/new-app.component';
import {EditAppComponent} from './routes/root/user/edit-app/edit-app.component';
import {UserComponent} from './routes/root/user/user.component';
import {AppDetailsComponent} from './routes/root/app-details/app-details.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'home', component: RootComponent,
    children: [
      {path: 'apps', component: HomeComponent},
      {path: 'apps/:id', component: AppDetailsComponent}
    ]
  },
  {
    path: 'user', component: RootComponent,
    children: [
      {
        path: 'myapps', component: UserComponent,
        children: [
          {path: 'new', component: NewAppComponent},
          {path: ':id', component: EditAppComponent}
        ]
      },
    ]
  },
  { path: 'login', component: LoginComponent},
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
