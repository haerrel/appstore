import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BackendService} from './Backend/backend.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angularclient';

  private apps: any;

  constructor() {}

  ngOnInit() {
    $(function () {
      $('[data-toggle="tooltip"]').tooltip()
    })
  }

}
