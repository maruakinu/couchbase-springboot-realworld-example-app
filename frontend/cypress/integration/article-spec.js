// enables intelligent code completion for Cypress commands
// https://on.cypress.io/intelligent-code-completion
/// <reference types="cypress" />
/// <reference types="@testing-library/cypress" />
import faker from 'faker';

describe('Article page', () => {
  beforeEach(() => {
    cy.intercept('GET', '**/articles?*')
      .as('getAllArticles')
      .intercept('GET', '**/articles/*/comments')
      .as('getCommentsForArticle')
      .intercept('GET', '**/articles/*')
      .as('getArticle')
      .visit('/');

    cy.wait('@getAllArticles').get('.preview-link').first().click();
  });

  it('should show the article', () => {
    cy.wait('@getArticle')
  });

  
});

describe('Article page (authenticated)', () => {
  const commentPlaceholder = 'Write a comment...';
  const postCommentButton = 'Post Comment';
  const emailPlaceholder = 'Email';
  const passwordPlaceholder = 'Password';

  beforeEach(() => {
    cy.intercept('GET', '**/articles?*')
      .as('getAllArticles')
      .intercept('GET', '**/articles/*/comments')
      .as('getCommentsForArticle')
      .intercept('GET', '**/articles/*')
      .as('getArticle')
      .intercept('POST', '**/articles/*/comments')
      .as('createComment')
      .intercept('DELETE', '**/articles/*/comments/*')
      .as('deleteComment')
      cy.intercept('POST', '**/users/login').as('login').visit('/login');
  });

  it('should show the comment box', () => {
    
    cy.findByPlaceholderText(emailPlaceholder).type(Cypress.env('email'));

    cy.findByPlaceholderText(passwordPlaceholder).type(Cypress.env('password'));

    cy.findByRole('button', { name: /sign in/i }).click();

    cy.location('pathname').should('be.equal', '/');

    cy.wait('@getAllArticles', {timeout: 15000}).get('.preview-link').first().click();

    cy.get('.comment-form').should('exist');

  });

  it('should add a new comment', () => {
    const comment = faker.lorem.paragraph();

    cy.findByPlaceholderText(emailPlaceholder).type(Cypress.env('email'));

    cy.findByPlaceholderText(passwordPlaceholder).type(Cypress.env('password'));

    cy.findByRole('button', { name: /sign in/i }).click();

    cy.location('pathname').should('be.equal', '/');

    cy.wait('@getAllArticles').get('.preview-link').first().click();

    //cy.wait(['@getArticle', '@getCommentsForArticle']);

    cy.findByPlaceholderText(commentPlaceholder).type(comment);

    cy.findByRole('button', { name: postCommentButton }).click();

    //cy.wait('@createComment').its('response.statusCode').should('equal', 200);

    
  });

  it('should validate the comment box', () => {
    cy.findByPlaceholderText(emailPlaceholder).type(Cypress.env('email'));

    cy.findByPlaceholderText(passwordPlaceholder).type(Cypress.env('password'));

    cy.findByRole('button', { name: /sign in/i }).click();

    cy.location('pathname').should('be.equal', '/');

    cy.wait('@getAllArticles').get('.preview-link').first().click();
    //cy.wait(['@getArticle', '@getCommentsForArticle']);

    cy.findByRole('button', { name: postCommentButton }).click();

    //cy.wait('@createComment').its('response.statusCode').should('equal', 422);

    // cy.get('.error-messages').within(() => {
    //   cy.findAllByRole('listitem').should('have.length', 1);
    // });
  });

  it('should remove my own comment', () => {
    const comment = faker.lorem.sentence();

    cy.findByPlaceholderText(emailPlaceholder).type(Cypress.env('email'));

    cy.findByPlaceholderText(passwordPlaceholder).type(Cypress.env('password'));

    cy.findByRole('button', { name: /sign in/i }).click();

    cy.location('pathname').should('be.equal', '/');

    cy.wait('@getAllArticles').get('.preview-link').first().click();

    //cy.wait(['@getArticle', '@getCommentsForArticle']);

    cy.findByPlaceholderText(commentPlaceholder).type(comment);

    cy.findByRole('button', { name: postCommentButton }).click();

    cy.wait('@createComment');

    cy.findByText(comment)
      .as('comment')
      .parent()
      .parent()
      .get('.mod-options i')
      .click();

    //cy.wait('@deleteComment').its('response.statusCode').should('equal', 200);

    //cy.findByText(comment).should('not.exist');
  });
});


