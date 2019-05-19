import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private endpoint = environment.baseUrl;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.post<any>(
        this.endpoint + 'login',
        {username, password}
      ).toPromise()
        .then(res => {
          this.setSession(res.token);
          resolve();
        })
        .catch(err => reject(err));
    });

  }

  private setSession(authResult) {
    const decoded = jwt_decode(authResult);
    localStorage.setItem('id_token', authResult);
    localStorage.setItem('username', decoded.sub );
    localStorage.setItem('role', decoded.role );
    localStorage.setItem('expires_at', decoded.exp );
  }

  logout() {
    localStorage.removeItem('id_token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    localStorage.removeItem('expires_at');
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem('expires_at');
    return moment(expiration, 'X');
  }

  getUsername() {
    return localStorage.getItem('username');
  }

}
