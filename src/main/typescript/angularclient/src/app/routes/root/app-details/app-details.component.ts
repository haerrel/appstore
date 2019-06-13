import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
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

  constructor(private route: ActivatedRoute, private router: Router, private backend: BackendService, private search: SearchService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const appId = parseInt(params.get('id'), 10);
      if (appId) {
        this.backend.getApp(appId).subscribe(res => {
          this.app = res;
          if (this.sidebarApps.length === 0) {
            this.sidebarApps.push(res);
            const stringAppIds = this.route.snapshot.queryParamMap.getAll('apps');
            if (stringAppIds && Array.isArray(stringAppIds) && stringAppIds.length > 0) {
              const appIds = stringAppIds.map(id => parseInt(id, 10));
              this.backend.getAppsById(appIds).subscribe((items) => {
                this.sidebarApps = this.sidebarApps.concat(items);
              });
            }
          }
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
