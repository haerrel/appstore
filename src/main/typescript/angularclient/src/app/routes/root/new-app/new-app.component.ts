import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-new-app',
  templateUrl: './new-app.component.html',
  styleUrls: ['./new-app.component.css']
})
export class NewAppComponent implements OnInit {

  tags: Array<string> = [];
  images: Array<string> = [];

  constructor() { }

  ngOnInit() {
  }

  addTag(input) {
    if (input.value) {
      this.tags.push(input.value);
      input.value = "";
    }
  }

}
