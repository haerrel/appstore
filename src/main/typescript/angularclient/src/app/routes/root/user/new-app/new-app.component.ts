import { Component, OnInit } from '@angular/core';
import {BackendService} from '../../../../services/Backend/backend.service';
import {App} from '../../../../shared/app';
import {Tag} from '../../../../shared/tag';
import {MyToastrService} from "../../../../services/toast/my-toastr.service";
import * as moment from 'moment';

@Component({
  selector: 'app-new-app',
  templateUrl: './new-app.component.html',
  styleUrls: ['./new-app.component.css']
})
export class NewAppComponent implements OnInit {

  tags: Array<string> = [];
  description: string;
  title: string;
  link: string;
  price: string;

  constructor(private backend: BackendService, private toastr: MyToastrService) { }

  ngOnInit() {
  }

  loadThumbnail(file, onSucess) {
    if (file) {
      const reader = new FileReader();
      reader.onload = (evt: any) => {
        onSucess(evt.target.result);
      };
      reader.onerror = (evt: any) => {
        this.toastr.error('Unable to read file', 'Thumbnail');
      };
      reader.readAsDataURL(file);
    }
  }

  addTag(input) {
    if (input.value) {
      this.tags.push(input.value);
      input.value = '';
    }
  }

  postApp(thumbnailInput) {
    // TODO form validation here
    var self = this;
    this.loadThumbnail(thumbnailInput.files[0], (thumbData) => {
      var app = new App();
      app.text = this.description;
      app.title = this.title;
      app.price = 3; // TODO add field to html-form
      app.thumbnail = thumbData;
      app.tags = [];
      app.datePublished = moment().format('YYYY-MM-DD');
      self.tags.forEach(tag => {
        const newTag = new Tag();
        newTag.text = tag;
        app.tags.push(newTag);
      });
      self.backend.postApp(app).subscribe((res: App) => {
        this.toastr.success(`New App created, ID=${res.id}`, 'App');
      });
    });
  }

  extract(input) {
    const m = input.value.indexOf('#');
    const n = input.value.indexOf(' ', m);
    if (m !== -1 && n !== -1 && m < n) {
      const tag = input.value.substring(m + 1, n);
      this.tags.push(tag);
      input.value = input.value.replace(`#${tag} `, '');
    }
    return null;
  }


}
