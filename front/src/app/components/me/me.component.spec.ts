import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { expect } from '@jest/globals';

import { MeComponent } from './me.component';
import { User } from 'src/app/interfaces/user.interface';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  const mockUser: User = {
    id: 1,
    email: 'yoga@studio.com',
    lastName: 'LASTNAME',
    firstName: 'firstName',
    admin: true,
    password: 'string',
    createdAt: new Date(2024, 2, 20),
    updatedAt: new Date(2024, 3, 1),
  };

  const mockSnackBar = {
    open: jest.fn(),
  };

  const mockUserService = {
    delete: jest.fn().mockReturnValue(of({})), // Simuler une réponse observable vide
    getById: jest.fn().mockReturnValue(of({})),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: UserService, useValue: mockUserService },
        { provide: MatSnackBar, useValue: mockSnackBar },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call delete method with session data when form is submitted', () => {
    component.delete();
    expect(mockUserService.delete).toHaveBeenCalledWith(
      mockSessionService.sessionInformation.id.toString()
    );
    expect(mockSnackBar.open).toHaveBeenCalledWith(
      'Your account has been deleted !',
      'Close',
      { duration: 3000 }
    );
  });

  it('should display session details correctly', () => {
    mockUserService.getById.mockReturnValue(of(mockUser));
    component.ngOnInit();
    fixture.detectChanges(); // Mettre à jour le DOM avec les informations de l'utilisateur

    const userInfoElement = fixture.debugElement.query(
      By.css('mat-card-content')
    ).nativeElement;
    expect(userInfoElement.textContent).toContain(mockUser.firstName);
    expect(userInfoElement.textContent).toContain(mockUser.lastName);
    expect(userInfoElement.textContent).toContain(mockUser.email);
    expect(userInfoElement.textContent).toContain('You are admin');
  });
});
