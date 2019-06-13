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

  submitEnabled = true;
  app: App = new App();

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

  extract(input) {
    const m = input.value.indexOf('#');
    const n = input.value.indexOf(' ', m);
    if (m !== -1 && n !== -1 && m < n) {
      const tagText = input.value.substring(m + 1, n);
      const newTag = new Tag();
      newTag.text = tagText;
      this.app.tags.push(newTag);
      input.value = input.value.replace(`#${tagText} `, '');
    }
    return null;
  }

  getApp(appId: number) {
    if (appId) {
      this.backend.getApp(appId).subscribe(res => {
        this.app = res;
      });
    }
  }

  loadThumbnail(thumbnailInput) {
    this.submitEnabled = false;
    const file = thumbnailInput.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (evt: any) => {
        const thumbData = evt.target.result;
        this.app.thumbnail = thumbData;
        this.submitEnabled = true;
      };
      reader.onerror = (evt: any) => {
        this.toastr.error('Unable to read file', 'Thumbnail');
        this.submitEnabled = true;
      };
      reader.readAsDataURL(file);
    }
  }

  submit(thumbnailInput) {
    this.backend.putApp(this.app).subscribe((res: App) => {
      this.toastr.success(`App updated, ID=${res.id}`, 'App');
      // this.getApp(res.id);
      window.location.reload(); // TODO sonst wird sidebar nicht aktualisiert
    });
  }
}
