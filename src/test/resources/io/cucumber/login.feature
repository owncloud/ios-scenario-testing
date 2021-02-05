@login
Feature: Login

  As an user, i want to be able to login in my account basic, OAuth2 or OIDC
  so that i can manage the content inside

  Background: Skipping wizard
    Given wizard is skipped

  @smoke
  Scenario Outline: A valid login in basic auth
    When server with basic auth is available
    And user logins as <username> with password <password> as basic auth credentials
    Then user can see the main page

    Examples:
      | username     | password |
      |  user1       |    a     |
      |  e@solid     |    $%    |
      |  hola hola   |    a     |
      |  a+a         |    a     |

  @LDAP @smoke
  Scenario Outline: A valid login in LDAP
    When server with LDAP is available
    And user logins as <username> with password <password> as LDAP credentials
    Then user can see the main page

    Examples:
      | username        |   password   |
      |  aaliyah_adams  |    secret    |

  @redirect301 @smoke
  Scenario Outline: A valid login with 301 redirection
    When server with redirection 301 is available
    And user logins as <username> with password <password> as basic auth credentials
    Then user can see the main page

    Examples:
      | username |   password   |
      |  admin   |    admin     |

  @redirect302 @smoke
  Scenario Outline: A valid login with 302 redirection
    When server with redirection 302 is available
    And user logins as <username> with password <password> as basic auth credentials
    Then user can see the main page

    Examples:
      | username |   password   |
      |  admin   |    admin     |

  Scenario: An invalid login @smoke
    When server with basic auth is available
    And user logins as user1 with password as as basic auth credentials
    Then user sees an error message

  @OAuth2
  Scenario: A valid login with OAuth2
    When server with OAuth2 is available
    And user logins as user1 with password a as OAuth2 credentials
    Then user can see the main page

  @OIDC
  Scenario: A valid login with OIDC
    When server with OIDC is available
    And user logins as einstein with password relativity as OIDC credentials
    Then user can see the main page