Feature: Login

  Background: Valid user, skipping welcome wizard
    Given I am a valid user

  Scenario Outline: A valid login
    When I login as <username> with password <password>
    Then I can see the main page

    Examples:
      | username     | password |
      |  user1       |    a     |
      |  user2       |    a     |
      |  admin       |  admin   |
      |  hola hola   |    a     |
      |  e@solid     |    $%    |


  Scenario Outline: An invalid login
    When I login as <username> with incorrect password <password>
    Then I see an error message

    Examples:
      | username     | password |
      |  user1       |    as    |
      |  user2       |    as    |