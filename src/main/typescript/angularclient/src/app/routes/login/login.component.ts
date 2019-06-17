import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  constructor(private service: AuthService, private router: Router, private toastr: ToastrService) { }

  ngOnInit() {
    if (this.service.isLoggedIn()) {
      this.router.navigateByUrl('/home/apps');
    }
  }

  login(role: string) {
    let loginPromise = null;
    switch (role) {
      case 'ADMIN':
        loginPromise = this.service.login('DemoAdmin', 'DemoPassword');
        break;
      case 'DEVELOPER':
        loginPromise = this.service.login('DemoDeveloper', 'DemoPassword');
        break;
      case 'FORM':
        if (this.username && this.password) {
          loginPromise = this.service.login(this.username, this.password);
        }
        break;
    }
    if (loginPromise) {
      loginPromise
        .then(() => {
          this.toastr.success('You´ve successfully logged in!', 'Login', {'positionClass': 'toast-bottom-right'});
          this.router.navigateByUrl('/home/apps');
        })
        .catch((err) => {
          this.toastr.error('Your login was invalid!', 'Login', {'positionClass': 'toast-bottom-right'});
        });
    }
  }

}
