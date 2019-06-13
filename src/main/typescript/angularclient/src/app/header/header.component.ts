import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../services/auth/auth.service';
import {Router} from '@angular/router';
import {SearchService} from '../services/search/search.service';
import {Tag} from '../shared/tag';
import {Role} from '../shared/role';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @ViewChild('searchInput') searchInput;

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

  isAdmin(): boolean {
    return this.authService.getRole() === Role.ADMIN;
  }

  search() {
    const optTag = this.extractTag(this.searchInput.nativeElement.value);
    if (optTag) {
      const newTag = new Tag();
      newTag.text = optTag;
      this.searchService.addTag(newTag);
      this.searchInput.nativeElement.value = this.removeTagFromInput(this.searchInput.nativeElement.value, optTag);
    }
    this.searchService.search(this.searchInput.nativeElement.value);
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

  searchButtonClick() {
    this.search();
    this.router.navigateByUrl('/home/apps');
  }
}
