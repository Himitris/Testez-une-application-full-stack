import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { By } from '@angular/platform-browser';
import { Session } from '../../interfaces/session.interface';
import { Teacher } from 'src/app/interfaces/teacher.interface';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from 'src/app/services/teacher.service';
import { of } from 'rxjs';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let service: SessionService;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  const mockSession: Session = {
    name: 'Session 1',
    date: new Date(2024, 3, 6), // Par exemple, 6 Avril 2024
    description: 'Première session',
    users: Array(10).fill({}), // Simulez 10 participants
    createdAt: new Date(2024, 2, 20),
    updatedAt: new Date(2024, 3, 1),
    teacher_id: 2,
  };

  const mockTeacher: Teacher = {
    id: 2,
    lastName: 'THIERCELIN',
    firstName: 'Hélène',
    createdAt: new Date(2024, 2, 20),
    updatedAt: new Date(2024, 3, 1),
  };

  // Service mocké pour SessionApiService
  const mockSessionApiService = {
    detail: jest.fn().mockReturnValue(of(mockSession)),
    delete: jest.fn().mockReturnValue(of({})), // Simulez la réponse de suppression
  };

  // Service mocké pour TeacherService
  const mockTeacherService = {
    detail: jest.fn().mockReturnValue(of(mockTeacher)),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: TeacherService, useValue: mockTeacherService },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
    service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display Delete button', async () => {
    // Récupérez tous les éléments button, puis filtrez-les pour trouver celui qui contient le texte 'Delete'
    expect(
      fixture.debugElement
        .queryAll(By.css('button'))
        .find((button) => button.nativeElement.textContent.includes('Delete'))
    ).toBeTruthy();
  });

  it('should call delete method with session data when form is submitted', () => {
    // First, initialize the component and its form with valid data
    component.ngOnInit();
    fixture.detectChanges(); // Detect changes to ensure the form is updated
    component.sessionId = '2';
    // Simulate form submission
    component.delete();

    // Check if the create method was called with the expected data
    expect(mockSessionApiService.delete).toHaveBeenCalledWith('2');
  });

  it('should display session details correctly', () => {
    const sessionNameElement = fixture.debugElement.query(
      By.css('h1')
    ).nativeElement;
    expect(sessionNameElement.textContent).toContain('Session 1');
    // Vérifiez les détails de l'enseignant, si applicable
    if (component.teacher) {
      const teacherNameElement = fixture.debugElement.query(
        By.css('mat-card-subtitle span')
      ).nativeElement;
      expect(teacherNameElement.textContent).toContain('Hélène THIERCELIN');
    }
    const sessionDescriptionElement = fixture.debugElement.query(
      By.css('.description')
    ).nativeElement;
    expect(sessionDescriptionElement.textContent).toContain('Première session');
  });
});
