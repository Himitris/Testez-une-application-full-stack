describe('Details spec', () => {
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
