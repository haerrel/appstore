import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './routes/root/home/home.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { AppcardComponent } from './mixins/appcard/appcard.component';
import { LoginComponent } from './routes/login/login.component';
import { RootComponent } from './routes/root/root.component';
import {AuthInterceptor} from './services/auth/auth.interceptor';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule} from '@angular/forms';
import { NewAppComponent } from './routes/root/user/new-app/new-app.component';
import { UserComponent } from './routes/root/user/user.component';
import { EditAppComponent } from './routes/root/user/edit-app/edit-app.component';
import { AppDetailsComponent } from './routes/root/app-details/app-details.component';
import { StarRatingComponent } from './mixins/star-rating/star-rating.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    AppcardComponent,
    LoginComponent,
    RootComponent,
    NewAppComponent,
    UserComponent,
    EditAppComponent,
    AppDetailsComponent,
    StarRatingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
