@privatelink
Feature: Private Links

  As a user
  I want to open private links inside the app
  so that i will reach directly the referenced content

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario Outline: Item in root folder
    Given the following items have been created in Alice account
      | folder | privlink |
      | <type> | <path>   |
    When Alice opens a private link pointing to <path> with scheme owncloud
    Then <type> <name> is opened in the app

    Examples:
      | type   | path                      | name             |
      | file   | privlink/privateLink1.pdf | privateLink1.pdf |
      | folder | privlink/privateLink2     | privateLink2     |

  @nooc10 @sharedlink
  Scenario Outline: Item in shared jail
    Given Bob has shared <type> <name> with user Alice with Viewer permissions
    When Alice opens a private link pointing to shared <name> with scheme owncloud
    Then <type> <name> is opened in the app

    Examples:
      | type | name             |
      | file | privateLink3.txt |

  Scenario: Item not existing
    When Alice opens a private link pointing to non-existing item
    Then Alice should see a link resolution error
