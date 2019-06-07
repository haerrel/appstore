import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BackendService} from '../../../services/Backend/backend.service';
import {App} from '../../../shared/app';
import {SearchService} from '../../../services/search/search.service';
import {Tag} from '../../../shared/tag';

@Component({
  selector: 'app-app-details',
  templateUrl: './app-details.component.html',
  styleUrls: ['./app-details.component.css']
})
export class AppDetailsComponent implements OnInit {

  app: App = new App();
  sidebarApps: Array<App> = [];

  constructor(private route: ActivatedRoute, private backend: BackendService, private search: SearchService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const appId = parseInt(params.get('id'), 10);
      if (appId) {
        this.backend.getApp(appId).subscribe(res => {
          this.app = res;
          this.getSearch().getLastSearchResultWithSelectedApp(this.app.id)
            .then(apps => this.sidebarApps = apps)
            .catch(err => console.log(err));
        });
      }
    });
  }

  getSearch(): SearchService {
    return this.search;
  }

  addTagToSearch(tag: Tag) {
    this.search.addTag(tag);
  }
}
