@login
Feature: Login

  As an user, i want to be able to login in my account basic or OAuth2
  so that i can manage the content inside

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
      |  e@solid     |    $%    |

  Scenario Outline: An invalid login
    When server with basic auth is available
    And user logins as <username> with password <password> as basic auth credentials
    Then user sees an error message

    Examples:
      | username     | password |
      |  user1       |    as    |
      |  user2       |    as    |

  @NoEmulator
  Scenario: A valid login with OAuth2
    When server with OAuth2 is available
    And user logins as user1 with password a as OAuth2 credentials
    Then user can see the main page