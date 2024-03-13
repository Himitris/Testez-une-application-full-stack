import { HttpClientModule } from '@angular/common/http';
import {
  ComponentFixture,
  TestBed,
  fakeAsync,
  tick,
} from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { User } from 'src/app/interfaces/user.interface';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  const mockLogin: User = {
    id: 2,
    email: 'test@test.com',
    lastName: 'Lastname',
    firstName: 'firstname',
    admin: true,
    password: 'test',
    createdAt: new Date(2024, 2, 20),
    updatedAt: new Date(2024, 3, 1),
  };
  const routerMock = {
    navigate: jest.fn(),
  };

  const mockAuthService = {
    login: jest.fn().mockReturnValue(of(mockLogin)),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        SessionService,
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: mockAuthService },
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
      ],
    }).compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should connect', () => {
    component.form.controls['email'].setValue('yoga@studio.com');
    component.form.controls['password'].setValue('test!1234');

    component.submit();

    expect(mockAuthService.login).toHaveBeenCalledWith({
      email: 'yoga@studio.com',
      password: 'test!1234',
    });
    expect(component.onError).toBeFalsy();
  });

  it('should throw error', () => {
    component.form.controls['email'].setValue('yogaerror@studio.com');
    component.form.controls['password'].setValue('');

    jest
      .spyOn(mockAuthService, 'login')
      .mockReturnValue(throwError(() => new Error('Erreur de connexion')));

    component.submit();
    expect(component.form.valid).toBeFalsy();
    expect(component.onError).toBeTruthy();
  });

  it('should simulate an error for a specific email/password combination', () => {
    const failingEmail = 'yoga@studio.com';
    const failingPassword = 'wrongpassword';

    jest.spyOn(mockAuthService, 'login').mockImplementation((loginRequest) => {
      if (
        loginRequest.email === failingEmail &&
        loginRequest.password === failingPassword
      ) {
        return throwError(
          () => new Error('Combinaison email/mot de passe incorrecte')
        );
      }
      return of({} as SessionInformation);
    });

    component.form.controls['email'].setValue('yoga@studio.com');
    component.form.controls['password'].setValue('wrongpassword');

    component.submit();

    // Vérifier que l'erreur est bien gérée dans le composant
    expect(component.onError).toBeTruthy();
  });
});
