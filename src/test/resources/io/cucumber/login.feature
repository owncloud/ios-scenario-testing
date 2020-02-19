Feature: Login

  Background: Valid user, skipping welcome wizard
    Given user is a valid user

  Scenario Outline: A valid login
    When server with basic auth is available
    And user logins as <username> with password <password> as basic auth credentials
    Then user can see the main page

    Examples:
      | username     | password |
      |  user1       |    a     |
      |  user2       |    a     |
      |  hola hola   |    a     |
      |  e@solid     |    $%    |


  Scenario Outline: An invalid login
    When server with basic auth is available
    And user logins as <username> with incorrect password <password>
    Then user sees an error message

    Examples:
      | username     | password |
      |  user1       |    as    |
      |  user2       |    as    |


  Scenario: A valid login with OAuth2
    When server with OAuth2 is available
    And user logins as user1 with password a as OAuth2 credentials
    Then user can see the main page