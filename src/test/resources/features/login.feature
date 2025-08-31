#Author: rvm.seng@gmail.com
#This sample is used for tutorial purposes.

@critical
Feature: Login functionality
  In order to access protected features
  As a registered user
  I want to be able to log in

  Background:
    Given the user is on the login page

  @happy
  Scenario: Successful login with valid credentials
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the user should see the inventory page

  @invalid
  Scenario Outline: Unsuccessful login with invalid credentials
    When the user logs in with username "<username>" and password "<password>"
    Then the user should see an error message containing "<message>"

    Examples:
      | username       | password      | message                    					|
      | standard_user  | wrong_pass    | Username and password do not match 	|
      | locked_user    | secret_sauce  | Sorry, this user has been locked out |
