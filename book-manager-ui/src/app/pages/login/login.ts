import { Component } from '@angular/core';
import { AuthenticationRequest } from '../../services/models/authentication-request';

@Component({
  imports: [],
  selector: 'app-login',
  styleUrl: './login.scss',
  templateUrl: './login.html',
})
export class Login {

  authRequest: AuthenticationRequest = { email: '', password: ''};
  errorMsg: Array<string> = [];
}
