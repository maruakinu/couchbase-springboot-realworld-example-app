// enables intelligent code completion for Cypress commands
// https://on.cypress.io/intelligent-code-completion
/// <reference types="cypress" />
/// <reference types="@testing-library/cypress" />
import faker from 'faker';

describe('Register page', () => {
  const usernamePlaceholder = 'Username';
  const emailPlaceholder = 'Email';
  const passwordPlaceholder = 'Password';

  beforeEach(() => {
    cy.intercept('POST', '**/users').as('register').visit('/register');
  });

  it('should submit the register form', () => {
    cy.findByPlaceholderText(usernamePlaceholder).type('Jonahton');

    cy.findByPlaceholderText(emailPlaceholder).type('Jona@gmail.com');

    cy.findByPlaceholderText(passwordPlaceholder).type('Pa$$w0rd!');

    cy.findByRole('button', { name: /sign up/i }).click();

    cy.wait('@register').its('response.statusCode').should('equal', 200);
  });

  it('should require all the fields', () => {
    cy.findByRole('button', { name: /sign up/i }).click();

  });

  it('should require the username', () => {
    cy.findByPlaceholderText(emailPlaceholder).type(
      faker.internet.exampleEmail().toLowerCase()
    );

    cy.findByPlaceholderText(passwordPlaceholder).type(
      faker.internet.password()
    );

    cy.findByRole('button', { name: /sign up/i }).click();


  });

  it('should require the email', () => {
    cy.findByPlaceholderText(usernamePlaceholder).type(
      faker.internet.userName().toLowerCase().replace(/\W/g, '_').substr(0, 20)
    );

    cy.findByPlaceholderText(passwordPlaceholder).type(
      faker.internet.password()
    );

    cy.findByRole('button', { name: /sign up/i }).click();

  });

  it('should require the password', () => {
    cy.findByPlaceholderText(usernamePlaceholder).type(
      faker.internet.userName().toLowerCase().replace(/\W/g, '_').substr(0, 20)
    );

    cy.findByPlaceholderText(emailPlaceholder).type(
      faker.internet.email().toLowerCase()
    );

    cy.findByRole('button', { name: /sign up/i }).click();

  });

});
