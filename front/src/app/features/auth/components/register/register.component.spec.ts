import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  const mockRegister: Object = {
    message: 'User registered successfully!',
  };
  const routerMock = {
    navigate: jest.fn(),
  };
  const mockAuthService = {
    register: jest.fn().mockReturnValue(of(mockRegister)),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: mockAuthService },
      ],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should connect', () => {

    // Configurez votre composant et le formulaire comme avant
    component.form.controls['email'].setValue('yogatest@studio.com');
    component.form.controls['password'].setValue('test!1234');
    component.form.controls['firstName'].setValue('test');
    component.form.controls['lastName'].setValue('test');

    // Appelez la méthode submit
    component.submit();

    // Effectuez vos assertions
    expect(mockAuthService.register).toHaveBeenCalledWith({
      email: 'yogatest@studio.com',
      password: 'test!1234',
      firstName: 'test',
      lastName: 'test',
    });
    expect(component.onError).toBeFalsy();
  });

  it('should throw error because something empty', () => {
    component.form.controls['email'].setValue('yogaerror@studio.com');
    component.form.controls['password'].setValue('');
    component.form.controls['firstName'].setValue('test');
    component.form.controls['lastName'].setValue('test');

    jest
      .spyOn(mockAuthService, 'register')
      .mockReturnValue(throwError(() => new Error('Erreur de connexion')));

    component.submit();
    expect(component.form.valid).toBeFalsy();
    expect(component.onError).toBeTruthy();
  });
});

