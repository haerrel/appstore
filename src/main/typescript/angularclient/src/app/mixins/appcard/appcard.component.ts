import {Component, Input, OnInit} from '@angular/core';
import {App} from '../../shared/app';

@Component({
  selector: 'app-appcard',
  templateUrl: './appcard.component.html',
  styleUrls: ['./appcard.component.css']
})
export class AppcardComponent implements OnInit {

  @Input() app: App;

  constructor() { }

  ngOnInit() {
  }

}
