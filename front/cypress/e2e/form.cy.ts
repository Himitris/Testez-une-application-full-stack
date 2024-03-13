describe('empty spec', () => {

  Cypress.Commands.add('login', (email, password) => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
      },
    });
    cy.intercept('GET', '/api/session', {
      body: [
        {
          id: 2,
          name: 'Session 1',
          date: '2025-02-24T00:00:00.000+00:00',
          teacher_id: 2,
          description: 'Première session',
        },
        {
          id: 3,
          name: 'Session 2',
          date: '2025-02-24T00:00:00.000+00:00',
          teacher_id: 2,
          description: 'Deuxième session',
        },
      ],
    }).as('sessions');

    cy.intercept('POST', '/api/session', {
      body: {
        id: 2,
        name: 'Session 1',
        date: '2025-02-24T00:00:00.000+00:00',
        teacher_id: 2,
        description: 'Première session',
      },
    }).as('sessions');

    cy.intercept('GET', '/api/session/2', {
      body: {
        id: 2,
        createdAt: '2024-03-04T10:20:29',
        updatedAt: '2024-03-04T10:20:29',
        date: '2025-02-24T00:00:00.000+00:00',
        description: 'Première session',
        name: 'Session 1',
        teacher_id: 2,
        users: [2],
      },
    }).as('session');

    cy.intercept('PUT', '/api/session/2', {});

    cy.intercept('GET', '/api/teacher', {
      body: [
        {
          id: 1,
          lastName: 'DELAHAYE',
          firstName: 'Margot',
          createdAt: '2024-03-04T09:39:44',
          updatedAt: '2024-03-04T09:39:44',
        },
        {
          id: 2,
          lastName: 'THIERCELIN',
          firstName: 'Hélène',
          createdAt: '2024-03-04T09:39:44',
          updatedAt: '2024-03-04T09:39:44',
        },
      ],
    });

    cy.get('input[formControlName=email]').type(`${email}`);
    cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`);
    cy.url().should('include', '/sessions');
  });
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
  });
  
  it('should update correctly', () => {
    cy.contains('Edit').click();
    cy.get('input[formControlName=name]').type(`{backspace}2`);
    cy.contains('Save').click();
    cy.contains('Session updated !').should('be.visible');
  });

  it('should create correctly', () => {
    cy.contains('Create').click();
    cy.get('input[formControlName=name]').type('Session 1');
    cy.get('input[formControlName=date]').type('2025-02-24');
    cy.get('mat-select[formControlName=teacher_id]')
      .click()
      .get('mat-option')
      .contains('Margot DELAHAYE')
      .click();
    cy.get('[formControlName=description]').type('Session numéro une');
    cy.contains('Save').click();
    cy.contains('Session created !').should('be.visible');
  });

  it('should disable delete button when field missing on create', () => {
    cy.contains('Create').click();
    cy.get('input[formControlName=name]').type(' ');
    cy.get('input[formControlName=date]').click();
    cy.get('[formControlName=description]').type(' ');
    cy.contains('button', 'Save').should('be.disabled');
  });

   it('should disable delete button when field missing on update', () => {
    cy.contains('Edit').click();
    cy.get('input[formControlName=name]').clear();
    cy.contains('button', 'Save').should('be.disabled');
   });
})