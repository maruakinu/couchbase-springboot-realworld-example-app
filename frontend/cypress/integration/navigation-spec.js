// enables intelligent code completion for Cypress commands
// https://on.cypress.io/intelligent-code-completion
/// <reference types="cypress" />
/// <reference types="@testing-library/cypress" />

describe('Navigation', () => {
  beforeEach(() => {
    cy.intercept('**/articles?*')
      .as('getAllArticles')
      .intercept('**/tags')
      .as('getAllTags')
      .intercept('**/articles/*/comments')
      .as('getCommentsForArticle')
      .intercept('**/articles/*')
      .as('getArticle')
      .visit('/');
  });

  it('should navigate to the login page', () => {
    cy.findByRole('link', { name: /sign in/i }).click();

    cy.location('pathname').should('be.equal', '/login');

    cy.findByRole('heading', { name: /sign in/i });
  });

  it('should navigate to the register page', () => {
    cy.findByRole('link', { name: /sign up/i }).click();

    cy.location('pathname').should('be.equal', '/register');

    cy.findByRole('heading', { name: /sign up/i });
  });

  it('should navigate to the first article', () => {
    cy.get('.preview-link')
      .first()
      .within(() => {
        cy.findByText('Read more...').click();


        cy.location('pathname').should('match', /\/article\/[\w-]+/);
      });
  });


  it('should navigate by tag', () => {
    cy.wait('@getAllTags')
      .its('response.body')
      .then(({ tags }) => {
        let tag = tags[Math.floor(Math.random() * tags.length)];

        cy.get('.sidebar').findByText(tag).click();


        cy.get('.feed-toggle').findByText(tag).should('have.class', 'active');


        tag = tags[Math.floor(Math.random() * tags.length)];

        cy.get('.sidebar').findByText(tag).click();


      });
  });


});

describe('Navigation (authenticated)', () => {
  beforeEach(() => {
    cy.intercept('**/articles?*')
      .as('getAllArticles')
      .intercept('**/tags')
      .as('getAllTags')
      .intercept(`**/profiles/*`)
      .as('getProfile')
      .visit('/')
      //.login();
  });

  it('should switch between tabs', () => {
    cy.findByRole('button', {
      name: /global feed/i,
    }).click();

    cy.wait('@getAllArticles').its('response.statusCode').should('equal', 200);


  });

  it('should navigate to new post page', () => {
    cy.wait('@getAllTags');

  });




  it('should navigate to my favorited articles page', () => {
    cy.findByRole('navigation')

      .click();

  
  });
});
