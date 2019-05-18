import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BackendService} from './services/Backend/backend.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angularclient';

  private apps: any;

  constructor() {}

}
