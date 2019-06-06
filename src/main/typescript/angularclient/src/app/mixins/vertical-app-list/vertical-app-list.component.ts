import {Component, Input, OnInit} from '@angular/core';
import {App} from '../../shared/app';

@Component({
  selector: 'app-vertical-app-list',
  templateUrl: './vertical-app-list.component.html',
  styleUrls: ['./vertical-app-list.component.css']
})
export class VerticalAppListComponent implements OnInit {

  @Input() apps: Array<App>;
  @Input() routerPrefix: string;

  constructor() { }

  ngOnInit() {
  }



}
