@login @ignore
Feature: Login

  As a user
  I want to be able to login in my account basic, OAuth2 or OIDC
  So that i can manage the content inside


  @smoke
  Scenario Outline: A valid login in basic auth
    Given server with basic auth is available
    And user logins as <username> with password <password> as basic auth credentials
    Then user should be correctly logged

    Examples:
      | username     | password |
      |  user1       |    a     |
      |  e@solid     |    $%    |
      |  hola hola   |    a     |
      |  a+a         |    a     |

  @LDAP
  Scenario: A valid login in LDAP
    Given server with LDAP is available
    When user logins as aaliyah_adams with password secret as LDAP credentials
    Then user should be correctly logged


  @redirect301
  Scenario: A valid login with 301 redirection
    Given server with redirection 301 is available
    When user accepts the redirection
    And user logins as admin with password admin as basic auth credentials
    Then user should be correctly logged

  @smoke
  Scenario: An invalid login, with wrong credentials
    Given server with basic auth is available
    When user logins as user1 with password as as basic auth credentials
    Then user should see an error