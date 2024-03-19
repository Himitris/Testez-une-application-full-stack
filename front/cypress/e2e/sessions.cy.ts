describe('Sessions spec', () => {

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
