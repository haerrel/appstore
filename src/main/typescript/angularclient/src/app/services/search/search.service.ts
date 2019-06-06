import { Injectable } from '@angular/core';
import {App} from '../../shared/app';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  tags: Set<string> = new Set();
  apps: Array<App> = [];

  constructor() { }


  addTags(tag: string) {
    this.tags.add(tag);
  }
}
