import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth/auth.service';
import {Router} from '@angular/router';
import {SearchService} from '../services/search/search.service';
import {Tag} from '../shared/tag';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private searchService: SearchService) {
  }

  ngOnInit() {
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  getUsername(): string {
    return this.authService.getUsername();
  }

  search(input) {
    const optTag = this.extractTag(input.value);
    if (optTag) {
      const newTag = new Tag();
      newTag.text = optTag;
      this.searchService.addTag(newTag);
      input.value = this.removeTagFromInput(input.value, optTag);
    }
    this.searchService.search(input.value);
  }

  remove(tag: Tag) {
    this.searchService.remove(tag);
  }

  private removeTagFromInput(value: string, tag: string) {
    return value.replace(`#${tag} `, '');
  }

  getTags(): Set<Tag> {
    return this.searchService.getTags();
  }


  private extractTag(input) {
    const m = input.indexOf('#');
    const n = input.indexOf(' ', m);
    if (m !== -1 && n !== -1 && m < n) {
      return input.substring(m + 1, n);
    }
    return null;
  }

}
