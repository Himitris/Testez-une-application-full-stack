describe('sessions spec', () => {
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

    cy.get('input[formControlName=email]').type(`${email}`);
    cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`);

    cy.url().should('include', '/sessions');
  });

  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
  });

  it('should display list correctly', () => {
    cy.contains('Session 1').should('be.visible');
    cy.contains('Session 2').should('be.visible');
    cy.contains('Première session').should('be.visible');
    cy.contains('Deuxième session').should('be.visible');
    cy.contains('Detail').should('be.visible');
    cy.contains('Edit').should('be.visible');
    cy.get('.items.mt2').children().should('have.length', 2);
  });
});
