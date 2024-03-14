describe('empty spec', () => {

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