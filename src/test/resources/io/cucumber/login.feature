Feature: Login

  Scenario Outline: A valid login
    Given I am a valid user
    When I login as <username> with password <password>
    Then I can see the main page

    Examples:
      | username     | password |
      |  user1       |    a     |
      |  user2       |    a     |
      |  admin       |  admin   |
      |  hola hola   |    a     |
      |  e@solid     |    a     |


  Scenario Outline: An invalid login
    Given I am a valid user
    When I login as <username> with incorrect password <password>
    Then I see an error message

    Examples:
      | username     | password |
      |  user1       |    as    |
      |  user2       |    as    |