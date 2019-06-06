import { Component, OnInit } from '@angular/core';
import {App} from '../../../shared/app';
import {BackendService} from '../../../services/Backend/backend.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  apps: Array<App>;

  constructor(private backend: BackendService) { }

  ngOnInit() {
    this.backend.getAppsOfUser().subscribe(res => {
      this.apps = res;
    });
  }

}
