import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private username: string;
  private password: string;

  constructor(private service: AuthService, private router: Router) { }

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
        // TODO some UX feedback that you´ve successfully logged in
        this.router.navigateByUrl('/');
      })
      .catch(err => {
        // TODO some UX feedback that your login was invalid
        console.log('invalid login');
      });
  }

}
