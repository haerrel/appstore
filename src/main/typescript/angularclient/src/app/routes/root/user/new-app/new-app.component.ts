import { Component, OnInit } from '@angular/core';
import {BackendService} from '../../../../services/Backend/backend.service';
import {App} from '../../../../shared/app';
import {ToastrService} from 'ngx-toastr';
import {Tag} from '../../../../shared/tag';

@Component({
  selector: 'app-new-app',
  templateUrl: './new-app.component.html',
  styleUrls: ['./new-app.component.css']
})
export class NewAppComponent implements OnInit {

  tags: Array<string> = [];
  text: string;
  title: string;

  constructor(private backend: BackendService, private toastr: ToastrService) { }

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
    this.loadThumbnail(thumbnailInput.files[0], (thumbData) => {
      const app = new App();
      app.text = this.text;
      app.title = this.title;
      app.price = 3; // TODO add field to html-form
      app.thumbnail = thumbData;
      this.tags.forEach(tag => {
        const newTag = new Tag();
        newTag.text = tag;
        app.tags.push(newTag);
      });
      this.backend.postApp(app).subscribe((res: App) => {
        this.toastr.success(`New App created, ID=${res.id}`, 'App');
      });
    });
  }

}
