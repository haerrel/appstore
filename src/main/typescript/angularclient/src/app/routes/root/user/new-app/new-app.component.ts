import { Component, OnInit } from '@angular/core';
import {BackendService} from '../../../../services/Backend/backend.service';
import {App} from '../../../../shared/app';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-new-app',
  templateUrl: './new-app.component.html',
  styleUrls: ['./new-app.component.css']
})
export class NewAppComponent implements OnInit {

  tags: Array<string> = [];
  images: Array<string> = [];
  text: string;
  title: string;

  constructor(private backend: BackendService, private toastr: ToastrService) { }

  ngOnInit() {
  }

  addTag(input) {
    if (input.value) {
      this.tags.push(input.value);
      input.value = "";
    }
  }

  postApp() {
    // TODO form validation here
    const app = new App();
    app.text = this.text;
    app.title = this.title;
    app.price = 3; // TODO add field to html-form
    app.tags = this.tags.join(',');
    this.backend.postApp(app).subscribe((res: App) => {
      this.toastr.success(`New App created, ID=${res.id}`, 'App');
    });
  }

}
