#Author: rvm.seng@gmail.com
#This sample is used for tutorial purposes.

@regression
Feature: Login functionality

  Scenario Outline: Login with multiple users
    Given the user is on the login page
    When the user logs in with "<username>" and "<password>"
    Then the result should be "<expected>"

    Examples: 
      | username        | password     | expected |
      | standard_user   | secret_sauce | success  |
      | locked_out_user | secret_sauce | locked   |
