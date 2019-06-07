import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {App} from '../../shared/app';
import {environment} from '../../../environments/environment';
import {AuthService} from '../auth/auth.service';
import {Tag} from '../../shared/tag';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  private endpoint = environment.baseUrl;

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  constructor(private http: HttpClient, private auth: AuthService) { }

  getFamousApps(limit: number = 50) {
    return this.http.get<App[]>(this.endpoint + 'apps?filter=famous&limit=' + limit);
  }

  getCheapestApps(limit: number = 50) {
    return this.http.get<App[]>(this.endpoint + 'apps?filter=cheapest&limit=' + limit);
  }

  getNewestApps(limit: number = 50) {
    return this.http.get<App[]>(this.endpoint + 'apps?filter=newest&limit=' + limit);
  }

  getApp(id: number) {
    return this.http.get<App>(this.endpoint + 'apps/' + id);
  }

  putApp(app: App) {
    return this.http.put(this.endpoint + 'apps/' + app.id, app, {});
  }

  postApp(app: App) {
    return this.http.post(this.endpoint + 'apps', app, {});
  }

  getAppsOfUser() {
    const user = this.auth.getUsername();
    return this.http.get<App[]>(this.endpoint + 'apps?limit=10'); // TODO only get user apps
  }

  getApps(searchPrefix: string, tags: Set<Tag>) {
    return this.http.get<App[]>(this.endpoint + 'apps?search=' + searchPrefix + '&tags=' + Array.from(tags).map(tag => tag.text).join(','));
  }
}
