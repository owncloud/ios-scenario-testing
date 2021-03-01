@login
Feature: Login

  As an user, i want to be able to login in my account basic, OAuth2 or OIDC
  so that i can manage the content inside


  Scenario Outline: A valid login in basic auth
    When server with basic auth is available
    And user logins as user1 with password a as basic auth credentials
    Then user is correctly logged

    Examples:
      | username     | password |
      |  user1       |    a     |
      |  e@solid     |    $%    |
      |  hola hola   |    a     |
      |  a+a         |    a     |
