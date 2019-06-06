import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BackendService} from '../../../../services/Backend/backend.service';
import {App} from '../../../../shared/app';

@Component({
  selector: 'app-edit-app',
  templateUrl: './edit-app.component.html',
  styleUrls: ['./edit-app.component.css']
})
export class EditAppComponent implements OnInit {

  app: App = new App();
  tags: Array<string> = [];
  images: Array<string> = [];

  constructor(private route: ActivatedRoute, private backend: BackendService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const appId = parseInt(params.get('id'), 10);
      if (appId) {
        this.backend.getApp(appId).subscribe(res => {
          this.app = res;
        });
      }
    });
  }

  addTag(input) {
    if (input.value) {
      this.tags.push(input.value);
      input.value = "";
    }
  }

}
