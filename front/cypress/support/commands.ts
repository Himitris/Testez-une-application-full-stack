// ***********************************************
// This example namespace declaration will help
// with Intellisense and code completion in your
// IDE or Text Editor.
// ***********************************************
// declare namespace Cypress {
//   interface Chainable<Subject = any> {
//     customCommand(param: any): typeof customCommand;
//   }
// }
//
// function customCommand(param: any): void {
//   console.warn(param);
// }
//
// NOTE: You can use it like so:
// Cypress.Commands.add('customCommand', customCommand);
//
// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

declare namespace Cypress {
  interface Chainable {
    login(email, password): void;
  }
}

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
