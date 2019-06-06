import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.css']
})
export class StarRatingComponent implements OnInit {

  @Input() rating: number;
  @Input() overall: number;

  constructor() { }

  ngOnInit() {
  }

  emptyStars() {
    return this.overall - this.rating;
  }

  filledStars() {
    return this.rating;
  }
}
