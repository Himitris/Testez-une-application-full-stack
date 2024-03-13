import { HttpClientModule } from '@angular/common/http';
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { ListComponent } from './list.component';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { SessionApiService } from '../../services/session-api.service';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  const mockSessions = [
    {
      id: 1,
      name: 'Yoga for Beginners',
      date: new Date(2024, 3, 5),
      description: 'A beginner session for yoga enthusiasts.',
    },
    {
      id: 2,
      name: 'Advanced Pilates',
      date: new Date(2024, 3, 6),
      description: 'An advanced level pilates class for experienced attendees.',
    },
  ];

  const mockSessionApiService = {
    all: jest.fn(() => of(mockSessions)),
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true,
    },
  };

  beforeEach(async () => {
    TestBed.resetTestingModule();
    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [HttpClientModule, MatCardModule, MatIconModule],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should button create and edit appear if user is admin', () => {
    expect(
      fixture.debugElement.query(By.css('button[routerLink="create"]'))
    ).toBeTruthy();
    expect(
      fixture.debugElement
        .queryAll(By.css('button'))
        .find((button) => button.nativeElement.textContent.includes('Edit'))
    ).toBeTruthy();
  });

  it('should display sessions correctly', () => {
    const sessionItems = fixture.debugElement.queryAll(By.css('.item'));
    expect(sessionItems.length).toBe(mockSessions.length); // Vérifiez le nombre de sessions affichées

    // Vérifiez le contenu de la première session pour s'assurer que les données sont affichées correctement
    expect(
      sessionItems[0].query(By.css('mat-card-title')).nativeElement.textContent
    ).toContain(mockSessions[0].name);
    expect(
      sessionItems[0].query(By.css('mat-card-subtitle')).nativeElement
        .textContent
    ).toContain('Session on'); // Vous pouvez rendre cette vérification plus spécifique
    expect(
      sessionItems[0].query(By.css('p')).nativeElement.textContent
    ).toContain(mockSessions[0].description);
  });
});
