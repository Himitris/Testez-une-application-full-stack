describe('empty spec', () => {
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
