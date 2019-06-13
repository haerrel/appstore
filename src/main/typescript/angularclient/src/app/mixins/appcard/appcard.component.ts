import {Component, Input, OnInit} from '@angular/core';
import {App} from '../../shared/app';
import {ActivatedRoute, Router} from '@angular/router';
import {SearchService} from '../../services/search/search.service';

@Component({
  selector: 'app-appcard',
  templateUrl: './appcard.component.html',
  styleUrls: ['./appcard.component.css']
})
export class AppcardComponent implements OnInit {

  @Input() app: App;

  constructor(private router: Router, private search: SearchService, private route: ActivatedRoute) { }

  ngOnInit() {
  }

  showAppDetails() {
    const apps = this.search.getLastSearchResults().map(app => app.id).filter(id => id !== this.app.id);
    this.router.navigate( [this.router.url, this.app.id], {queryParams: {apps}});
  }
}
