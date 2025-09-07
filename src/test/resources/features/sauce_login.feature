
@demo
Feature: Login to SauceDemo

  As a valid user
  I want to log in to see products
  
  Background:
    Given the user is on the login page

  @happy
  Scenario: Successful login
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the inventory page should be visible

  @invalid
  Scenario: Invalid password shows error
    When the user logs in with username "standard_user" and password "wrong_pass"
    Then an error message should be shown
