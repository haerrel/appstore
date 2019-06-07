import { Component, OnInit } from '@angular/core';
import {BackendService} from '../../../services/Backend/backend.service';
import {App} from '../../../shared/app';
import {SearchService} from '../../../services/search/search.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private backend: BackendService, private search: SearchService) { }

  famousApps: App[] = [];
  cheapestApps: App[] = [];
  newestApps: App[] = [];

  ngOnInit() {
    this.backend.getFamousApps().subscribe(res => {
      this.famousApps = res;
    });
    this.backend.getNewestApps().subscribe(res => {
      this.newestApps = res;
    });
    this.backend.getCheapestApps().subscribe(res => {
      this.cheapestApps = res;
    });
  }

  isSearchEmpty(): boolean {
    return this.search.isEmpty();
  }

  getSearchApps() {
    return this.search.getLastSearchResults();
  }

  noTags() {
    return this.search.getTags().size === 0;
  }
}
