import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

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

}
