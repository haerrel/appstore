import { Injectable } from '@angular/core';
import {App} from '../../shared/app';
import {BackendService} from '../Backend/backend.service';
import {Tag} from '../../shared/tag';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  searchText = '';
  tags: Set<Tag> = new Set();
  apps: Array<App> = [];

  constructor(private backend: BackendService) { }

  addTag(tag: Tag) {
    this.tags.add(tag);
  }

  remove(tag: Tag) {
    this.tags.delete(tag);
  }

  getLastSearchResults() {
    return this.apps;
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

  isEmpty(): boolean {
    return this.searchText === '';
  }

  search(text) {
    this.searchText = text;
    this.backend.getApps(text, this.tags).subscribe(apps => {
      this.apps = apps;
    });
  }

  getTags(): Set<Tag> {
    return this.tags;
  }
}
