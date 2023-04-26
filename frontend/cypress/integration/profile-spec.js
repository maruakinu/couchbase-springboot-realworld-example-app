// enables intelligent code completion for Cypress commands
// https://on.cypress.io/intelligent-code-completion
/// <reference types="cypress" />
/// <reference types="@testing-library/cypress" />
import faker from 'faker';

describe('Profile page', () => {
  const emailPlaceholder = 'Email';
  const passwordPlaceholder = 'Password';
  const firstname = faker.name.firstName();
  const lastname = faker.name.lastName();
  const username =
    faker.internet
      .userName(firstname, lastname)
      .toLowerCase()
      .replace(/\W/g, '_') + '_redux';
  const email = faker.internet
    .email(firstname, lastname, 'redux.js.org')
    .toLowerCase();

  before(() => {
    
    cy.task('createUserWithArticle', { username, email });

    cy.intercept('GET', '**/user')
      .as('getCurrentUser')
      .intercept('GET', '**/articles?*')
      .as('getAllArticles')
      .intercept('GET', `**/profiles/${username}`)
      .as('getProfile')
      .intercept('GET', `**/articles?author=${encodeURIComponent(username)}*`)
      .as('getArticlesByAuthor')
      // .visit('/')
      // .login()
       .visit(`/@${username}`)
      //.wait(['@getCurrentUser', '@getProfile', '@getArticlesByAuthor']);

      cy.intercept('POST', '**/users/login').as('login').visit('/login');
  });

  afterEach(function () {
    if (this.currentTest.state === 'failed') {
      Cypress.runner.stop();
    }
  });

  it("should show the user's image in the banner", () => {

    cy.findByPlaceholderText(emailPlaceholder).type(Cypress.env('email'));

    cy.findByPlaceholderText(passwordPlaceholder).type(Cypress.env('password'));

    cy.findByRole('button', { name: /sign in/i }).click();

    cy.location('pathname').should('be.equal', '/');

    cy.findByRole('navigation').within(() => {
      cy.get('img').should('be.visible');

    
    });

    cy.get('.article-preview').its('length').should('be.above', 0);
  });

  it('should follow the user', () => {
    cy.intercept('POST', `**/profiles/${username}/follow`).as('followProfile');
    cy.intercept('GET', '**/articles?*').as('getAllArticles')

    cy.wait('@getAllArticles').get('.author').first().click();

    //cy.get('.btn-outline-secondary').click();

    cy.findByRole('button').get('.btn-outline-secondary').click();

    ///cy.wait('@followProfile').its('response.statusCode').should('equal', 200);
  });

  it('should unfollow the user', () => {
    cy.intercept('DELETE', `**/profiles/${username}/follow`).as(
      'unfollowProfile'
    );

    cy.findByRole('button').get('.btn-outline-secondary').click();

    cy.get('.btn-secondary').click();

    //cy.findByRole('button', { name: `Unfollow ${username}` }).click();

    //cy.wait('@unfollowProfile').its('response.statusCode').should('equal', 200);
  });
});
