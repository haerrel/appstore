import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BackendService} from '../../../../services/Backend/backend.service';
import {App} from '../../../../shared/app';
import {Tag} from '../../../../shared/tag';
import {MyToastrService} from "../../../../services/toast/my-toastr.service";

@Component({
  selector: 'app-edit-app',
  templateUrl: './edit-app.component.html',
  styleUrls: ['./edit-app.component.css']
})
export class EditAppComponent implements OnInit {

  app: App = new App();
  images: Array<string> = [];

  constructor(private route: ActivatedRoute, private backend: BackendService, private toastr: MyToastrService) { }

  ngOnInit() {
    this.refreshForm();
  }

  addTag(input) {
    if (input.value) {
      const newTag = new Tag();
      newTag.text = input.value;
      this.app.tags.push(newTag);
      input.value = "";
    }
  }

  refreshForm() {
    this.route.paramMap.subscribe(params => {
      const appId = parseInt(params.get('id'), 10);
      this.getApp(appId);
    });
  }

  getApp(appId: number) {
    if (appId) {
      this.backend.getApp(appId).subscribe(res => {
        this.app = res;
      });
    }
  }

  submit() {
    this.backend.putApp(this.app).subscribe((res: App) => {
      this.toastr.success(`App updated, ID=${res.id}`, 'App');
      this.getApp(res.id);
    });
  }
}
