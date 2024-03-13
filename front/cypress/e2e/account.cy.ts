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

    cy.intercept('GET', '/api/user/1', {
      body: {
        id: 1,
        firstName: 'Admin',
        lastName: 'Admin',
        email: 'yoga@studio.com',
        admin: false,
        createdAt: '2024-03-04T09:39:44',
        updatedAt: '2024-03-04T09:39:44',
      },
    });

    cy.get('input[formControlName=email]').type(`${email}`);
    cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`);
    cy.url().should('include', '/sessions');
  });
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
    cy.contains('Account').click();
  });
  it('should display infos correctly', () => {
    cy.url().should('include', '/me');
    cy.contains('Admin ADMIN').should('be.visible');
    cy.contains('yoga@studio.com').should('be.visible');
    cy.contains('Delete my account:').should('be.visible');
    cy.contains('March 4, 2024').should('be.visible');
  });

  it('should logout correctly', () => {
    cy.contains('Logout').click();
    cy.location('pathname').should('eq', '/');
    cy.get('app-root').children().should('have.length', 1);
  });
  
  it('should cal delete correctly', () => {
    cy.url().should('include', '/me');
    cy.intercept('DELETE', '/api/user/1', {});
    cy.contains('Detail').click();
    cy.contains('Your account has been deleted !').should('be.visible');
  });
});
