@privatelink
Feature: Private Links

  As a user
  I want to open private links inside the app
  so that i will reach directly the referenced content

  Background: User is logged in
    Given user Alice is logged in


  Scenario Outline: Item in root folder
    Given the following items have been created in the account
      | <type> | <name> |
    When Alice opens a private link pointing to <name> with scheme owncloud
    Then <type> <name> is opened in the app

    Examples:
      | type    |  name             |
      | file    | privateLink1.pdf  |
      | folder  | privateLink2      |

  Scenario: Item not existing
    When Alice opens a private link pointing to non-existing item
    Then Alice should see a link resolution error
