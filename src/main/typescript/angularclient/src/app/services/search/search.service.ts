import { Injectable } from '@angular/core';
import {App} from '../../shared/app';
import {BackendService} from '../Backend/backend.service';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  tags: Set<string> = new Set();
  apps: Array<App> = [];

  constructor(private backend: BackendService) { }


  addTags(tag: string) {
    this.tags.add(tag);
  }

  getLastSearchResultWithSelectedApp(appId: number): Promise<App[]> {
    return new Promise((resolve, reject) => {
      const appIdx = this.apps.findIndex(app => app.id === appId);
      if (appIdx === -1) {
        this.backend.getApp(appId).toPromise()
          .then(data => resolve([data]))
          .catch(err => reject(err));
      } else {
        const selectedApp = this.apps[appIdx];
        let newApps = this.apps.splice(appIdx, 1);
        newApps = Array.prototype.concat(selectedApp, newApps);
        return resolve(newApps);
      }
    });
  }
}
