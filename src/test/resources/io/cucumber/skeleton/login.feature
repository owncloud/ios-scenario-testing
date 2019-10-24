Feature: Login

  Scenario: A valid login
    Given I am a valid user
    When I login as demo
    Then I can see the main page