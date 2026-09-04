import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Api } from '../../services/api';
import { authenticate } from '../../services/functions';
import { AuthenticationRequest } from '../../services/models/authentication-request';

@Component({
  imports: [FormsModule],
  selector: 'app-login',
  styleUrl: './login.scss',
  templateUrl: './login.html',
})
export class Login {
  protected authRequest: AuthenticationRequest = {
    email: '',
    firstname: '',
    lastname: '',
    password: '',
  };
  protected errorMsg: string[] = [];
  protected successMsg = '';
  protected isSubmitting = false;

  constructor(private readonly api: Api) {}

  async login(): Promise<void> {
    this.errorMsg = [];
    this.successMsg = '';

    if (!this.authRequest.email || !this.authRequest.password) {
      this.errorMsg = ['Az e-mail-cím és a jelszó megadása kötelező.'];
      return;
    }

    this.isSubmitting = true;
    try {
      const response = await this.api.invoke(authenticate, { body: this.authRequest });

      if (!response.token) {
        this.errorMsg = ['A szerver nem küldött érvényes hozzáférési tokent.'];
        return;
      }

      localStorage.setItem('token', response.token);
      this.successMsg = 'Sikeres bejelentkezés.';
    } catch (error: unknown) {
      this.errorMsg = this.getErrorMessages(error);
    } finally {
      this.isSubmitting = false;
    }
  }

  register(): void {
    this.successMsg = '';
    this.errorMsg = ['A regisztrációs oldal még nincs elkészítve.'];
  }

  private getErrorMessages(error: unknown): string[] {
    if (!(error instanceof HttpErrorResponse)) {
      return ['Váratlan hiba történt. Kérlek, próbáld újra.'];
    }

    const validationErrors = error.error?.validationErrors;
    if (Array.isArray(validationErrors)) {
      return validationErrors.map(String);
    }

    const message = error.error?.error ?? error.error?.message;
    if (typeof message === 'string' && message.length > 0) {
      return [message];
    }

    if (error.status === 0) {
      return ['A szerver nem érhető el. Ellenőrizd, hogy a backend fut-e a 8088-as porton.'];
    }

    return ['Hibás e-mail-cím vagy jelszó.'];
  }
}
