describe('Login spec', () => {
  it('Login not found', () => {
    cy.visit('/logine');
    cy.contains('Page not found !').should('be.visible');
  });
  it('Login failled', () => {
    cy.visit('/login');

    cy.get('input[formControlName=email]').type('yogaerror@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.get('.error').should('contain', 'An error occurred');
  });
  it('Login successfull', () => {
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

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );
    cy.url().should('include', '/sessions');
    cy.contains('Rentals available').should('be.visible');
  });
});
