import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './routes/root/home/home.component';
import {LoginComponent} from './routes/login/login.component';
import {RootComponent} from './routes/root/root.component';
import {NewAppComponent} from './routes/root/user/new-app/new-app.component';
import {EditAppComponent} from './routes/root/user/edit-app/edit-app.component';
import {UserComponent} from './routes/root/user/user.component';
import {AppDetailsComponent} from './routes/root/app-details/app-details.component';
import {ReportProblemComponent} from './routes/root/report-problem/report-problem.component';
import {ShowProblemComponent} from './routes/root/show-problem/show-problem.component';
import {ShowAllProblemsComponent} from './routes/root/show-all-problems/show-all-problems.component';

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
      {path: 'apps/:id', component: AppDetailsComponent},
      {path: 'problem', component: ReportProblemComponent}
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
      {path: 'problem', component: ShowAllProblemsComponent},
      {path: 'problem/:id', component: ShowProblemComponent}
    ]
  },
  { path: 'login', component: LoginComponent},
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
