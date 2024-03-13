import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { SessionApiService } from './session-api.service';
import { TestBed } from '@angular/core/testing';
import { Session } from '../interfaces/session.interface';
import { expect } from '@jest/globals';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService],
    });
    service = TestBed.inject(SessionApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify(); // Vérifie qu'il n'y a pas de requêtes en attente
  });

  it('all() should make a GET request to retrieve all sessions', () => {
    const testSessions: Session[] = [
      {
        id: 1,
        name: 'Session 1',
        date: new Date(2024, 3, 5),
        description: 'Première session',
        teacher_id: 2,
        users: [],
      },
      {
        id: 2,
        name: 'Session 2',
        date: new Date(2024, 3, 6),
        description: 'Deuxième session',
        teacher_id: 2,
        users: [],
      },
    ];

    service.all().subscribe((sessions) => {
      expect(sessions).toEqual(testSessions);
    });

    const req = httpTestingController.expectOne('api/session');
    expect(req.request.method).toEqual('GET');
    req.flush(testSessions);
  });

  it('detail(id) should make a GET request to retrieve session details', () => {
    const testSession: Session = {
      id: 1,
      name: 'Session 1',
      date: new Date(2024, 3, 5),
      description: 'A beginner session for yoga enthusiasts.',
      teacher_id: 2,
      users: [],
    };
    const id = '1';

    service.detail(id).subscribe((session) => {
      expect(session).toEqual(testSession);
    });

    const req = httpTestingController.expectOne(`api/session/${id}`);
    expect(req.request.method).toEqual('GET');
    req.flush(testSession);
  });

  it('create(session) should make a POST request to create a session', () => {
    const newSession: Session = {
      id: 1,
      name: 'Session 1',
      date: new Date(2024, 3, 5),
      description: 'Première session',
      teacher_id: 2,
      users: [],
    };

    service.create(newSession).subscribe((session) => {
      expect(session).toEqual(newSession);
    });

    const req = httpTestingController.expectOne('api/session');
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(newSession);
    req.flush(newSession);
  });

  it('update(id, session) should make a PUT request to update a session', () => {
    const updatedSession: Session = {
      id: 1,
      name: 'Session 1',
      date: new Date(2024, 3, 5),
      description: 'Première session',
      teacher_id: 2,
      users: [],
    };
    const id = '1';

    service.update(id, updatedSession).subscribe((session) => {
      expect(session).toEqual(updatedSession);
    });

    const req = httpTestingController.expectOne(`api/session/${id}`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toEqual(updatedSession);
    req.flush(updatedSession);
  });

  it('delete(id) should make a DELETE request to remove a session', () => {
    const id = '1';

    service.delete(id).subscribe((response) => {
      expect(response).toEqual({});
    });

    const req = httpTestingController.expectOne(`api/session/${id}`);
    expect(req.request.method).toEqual('DELETE');
    req.flush({});
  });
});
