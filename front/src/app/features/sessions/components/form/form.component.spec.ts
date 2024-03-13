import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { DetailComponent } from '../detail/detail.component';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let router;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
    },
  };

  const mockFormBuilder = {
    group: jest.fn().mockReturnValue(
      new FormGroup({
        name: new FormControl('Test Session', Validators.required),
        date: new FormControl(expect.any(String), Validators.required),
        teacher_id: new FormControl('1', Validators.required),
        description: new FormControl('This is a test description.', [
          Validators.required,
          Validators.maxLength(2000),
        ]),
      })
    ),
  };

  const mockSessionApiService = {
    create: jest.fn().mockReturnValue(of({})),
    update: jest.fn().mockReturnValue(of({})),
    detail: jest.fn().mockReturnValue(of({})),
  };
  const routerMock = {
    navigate: jest.fn(),
    url: '/update',
  };

  const activatedRouteMock = {
    snapshot: {
      paramMap: {
        get: jest.fn().mockReturnValue('2'), // Simuler le retour de l'ID '2'
      },
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        NoopAnimationsModule,
      ],
      providers: [
        FormBuilder,
        { provide: SessionService, useValue: mockSessionService },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        { provide: Router, useValue: routerMock },
        { provide: SessionApiService, useValue: mockSessionApiService },
      ],
      declarations: [FormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  function setupSessionForm() {
    component.sessionForm = mockFormBuilder.group({
      name: ['Test Session', [Validators.required]],
      date: [new Date().toISOString().split('T')[0], [Validators.required]],
      teacher_id: ['1', [Validators.required]],
      description: [
        'This is a test description.',
        [Validators.required, Validators.maxLength(2000)],
      ],
    });
  }

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should disable the save button when form is invalid and enabled when valid', () => {
    component.ngOnInit(); // Assurez-vous que le formulaire est initialisé
    fixture.detectChanges(); // Détectez les changements pour que les liaisons de données se mettent à jour

    // Tenter de trouver le bouton de sauvegarde avant la configuration du formulaire
    let saveButton = fixture.debugElement.query(
      By.css('button[type="submit"]')
    );
    // Vérifier que le bouton n'est pas présent
    expect(saveButton).toBeNull();

    setupSessionForm();
    fixture.detectChanges();

    saveButton = fixture.debugElement.query(By.css('button[type="submit"]'));
    expect(saveButton).not.toBeNull();
    // Vérifier que le bouton est activé (pas désactivé)
    expect(saveButton.nativeElement.disabled).toBeFalsy(); // Vérifiez que le bouton est activé
  });

  it('should call create method with session data when form is submitted', () => {
    // First, initialize the component and its form with valid data
    component.ngOnInit();
    component.onUpdate = false;
    setupSessionForm();

    component.sessionForm?.controls['description'].setValue(
      'This is a test description.'
    );
    component.sessionForm?.controls['name'].setValue('Test Session');
    component.sessionForm?.controls['teacher_id'].setValue('1');
    component.sessionForm?.controls['date'].setValue('date');
    // Simulate form submission
    component.submit();
    fixture.detectChanges();

    // Check if the create method was called with the expected data
    expect(mockSessionApiService.create).toHaveBeenCalledWith({
      name: 'Test Session',
      date: expect.any(String), // Since the date is transformed, we expect any string here
      teacher_id: '1',
      description: 'This is a test description.',
    });
  });

  it('should call update method with session data when form is submitted', () => {
    fixture.detectChanges();
    // First, initialize the component and its form with valid data
    component.onUpdate = true;
    setupSessionForm();
    component.sessionForm?.controls['description'].setValue(
      'Ceci est une description mise à jour.'
    );
    component.sessionForm?.controls['name'].setValue('Session mise à jour');
    component.sessionForm?.controls['teacher_id'].setValue('2');
    component.sessionForm?.controls['date'].setValue('date');

    // Simulate form submission
    component.submit();
    fixture.detectChanges();

    // Check if the create method was called with the expected data
    expect(mockSessionApiService.update).toHaveBeenCalledWith('2', {
      name: 'Session mise à jour',
      date: expect.any(String), // Since the date is transformed, we expect any string here
      teacher_id: '2',
      description: 'Ceci est une description mise à jour.',
    });
  });
});
