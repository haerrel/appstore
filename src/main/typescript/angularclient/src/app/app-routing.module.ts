import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './routes/home/home.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'someOtherPath', component: HomeComponent},
  // { path: '**', component: PageNotFoundComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
