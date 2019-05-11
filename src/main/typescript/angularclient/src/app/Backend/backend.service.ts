import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {App} from '../shared/app';
import {environment} from '../../environments/environment';

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

  constructor(private http: HttpClient) { }

  getFamousApps(limit: number = 50) {
    return this.http.get<App[]>(this.endpoint + 'apps?filter=famous&limit=' + limit);
  }

  getCheapestApps(limit: number = 50) {
    return this.http.get<App[]>(this.endpoint + 'apps?filter=cheapest&limit=' + limit);
  }

  getNewestApps(limit: number = 50) {
    return this.http.get<App[]>(this.endpoint + 'apps?filter=newest&limit=' + limit);
  }

}
