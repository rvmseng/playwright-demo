#Author: rvm.seng@gmail.com
#This sample is used for tutorial purposes.
@regression
Feature: Login with external data

  @debug
  Scenario: Validate login for multiple users from external data
    Given I have user data from json file
    When I perform login for each user
    Then I should see the login result in logs
