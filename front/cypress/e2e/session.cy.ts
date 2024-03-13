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

    cy.intercept('GET', '/api/session/2', {
      body: {
        id: 2,
        createdAt: '2024-03-04T10:20:29',
        updatedAt: '2024-03-04T10:20:29',
        date: '2025-02-24T00:00:00.000+00:00',
        description: 'Première session',
        name: 'Session 1',
        teacher_id: 2,
        users: [2]
      },
    }).as('session');

    cy.get('input[formControlName=email]').type(`${email}`);
    cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`);
    cy.url().should('include', '/sessions');
  });
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
    cy.contains('Detail').click();
  });

  it('should details display correctly', () => {
    cy.url().should('include', '/sessions/detail');
    cy.contains('Session 1').should('be.visible');
    cy.contains('Première session').should('be.visible');
    cy.contains('Create at: March 4, 2024').should('be.visible');
    cy.contains('February 24, 2025').should('be.visible');
    cy.contains('1 attendees').should('be.visible');
  });

  it('should display button and delete correctly', () => {
    cy.contains('button', 'Delete').should('be.visible');
    cy.intercept('DELETE', '/api/session/2', {} );
    cy.contains('Delete').click();
    cy.contains('Session deleted !').should('be.visible');
  });

});
