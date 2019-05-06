import { Component, OnInit } from '@angular/core';
import {BackendService} from '../../Backend/backend.service';
import {App} from '../../shared/app';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private backend: BackendService) { }

  famousApps: App[] = [];
  cheapestApps: App[] = [];
  newestApps: App[] = [];

  ngOnInit() {
    this.backend.getFamousApps(3).subscribe(res => {
      this.famousApps = res;
    });
    this.backend.getNewestApps(3).subscribe(res => {
      this.newestApps = res;
    });
    this.backend.getCheapestApps(3).subscribe(res => {
      this.cheapestApps = res;
    });
  }

}
