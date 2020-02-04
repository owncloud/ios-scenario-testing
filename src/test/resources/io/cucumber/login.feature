Feature: Login

  Background: Valid user, skipping welcome wizard
    Given user is a valid user

  Scenario Outline: A valid login
    When user logins as <username> with password <password>
    Then user can see the main page

    Examples:
      | username     | password |
      |  user1       |    a     |
      |  user2       |    a     |
      |  hola hola   |    a     |
      |  e@solid     |    $%    |


  Scenario Outline: An invalid login
    When user logins as <username> with incorrect password <password>
    Then user sees an error message

    Examples:
      | username     | password |
      |  user1       |    as    |
      |  user2       |    as    |