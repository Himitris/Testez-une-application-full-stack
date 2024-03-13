describe('Register spec', () => {
  it('Register successfull', () => {
    cy.visit('/register');

    cy.intercept('POST', '/api/auth/register', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false,
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/auth/register',
      },
      []
    ).as('login');

    cy.get('input[formControlName=firstName]').type('firstName');
    cy.get('input[formControlName=lastName]').type('lastName');
    cy.get('input[formControlName=email]').type('yoga2@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );
    cy.url().should('include', '/login');
  });

  it('Register failled', () => {
    cy.visit('/register');
    cy.get('input[formControlName=firstName]').type(' ');
    cy.get('input[formControlName=lastName]').type(' ');
    cy.get('input[formControlName=email]').type('yoga2@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );
    cy.get('.error').should('contain', 'An error occurred');
  });
});
