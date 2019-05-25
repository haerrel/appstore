import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {Router} from '@angular/router';

import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private username: string;
  private password: string;

  constructor(private service: AuthService, private router: Router, private toastr: ToastrService) { }

  ngOnInit() {
  }

  login(role: string) {
    // TODO some load spinner here
    let loginPromise = null;
    switch (role) {
      case 'ADMIN':
        loginPromise = this.service.login('DemoAdmin', 'DemoPassword');
        break;
      case 'DEVELOPER':
        loginPromise = this.service.login('DemoDeveloper', 'DemoPassword');
        break;
      case 'FORM':
        loginPromise = this.service.login(this.username, this.password);
    }
    loginPromise
      .then(() => {
        this.toastr.success("You´ve successfully logged in!", "Login");
        this.router.navigateByUrl('/apps');
      })
      .catch((err) => {
        this.toastr.error("Your login was invalid!", "Login")
      });
  }

}
