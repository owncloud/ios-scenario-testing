@shortcuts @nooc10
Feature: Shortcuts

  As a user
  I want to create a shortcut to any element
  So that, i can jump to any address from my account

  Background: User is logged in
    Given user Alice is logged in

  @createshortcut
  Rule: Create shortcut

  @smoke
  Scenario Outline: Create correct shortcut over URL
    When Alice selects the option Create Shortcut
    And Alice creates a web shortcut with the following fields
      | <url> | <name> |
    Then Alice should see <name>.url in the filelist

    Examples:
      | name      | url              |
      | shortcut1 | www.owncloud.org |

  Scenario Outline: Create correct shortcut over file
    Given the following items have been created in Alice account
      | <type> | <name>.txt |
    When Alice selects the option Create Shortcut
    And Alice creates a file shortcut with the following fields
      | <name>.txt | <name> |
    Then Alice should see <name>.url in the filelist

    Examples:
      | type | name      |
      | file | shortcut2 |

  @openshortcut
  Rule: Open shortcut

  @smoke @ignore
  Scenario Outline: Open shortcut over URL
    Given the following items have been created in Alice account
      | <type> | <name>.url |
    When Alice opens the shortcut <name>.url
    And Alice opens the link
    # Browser not matchable for latest versions of xcuitest driver
    Then Alice should see the browser

    Examples:
      | type     | name      |
      | shortcut | shortcut3 |

  Scenario Outline: Open shortcut over file
    Given the following items have been created in Alice account
      | <type> | <name>.txt |
    And Alice selects the option Create Shortcut
    And Alice creates a file shortcut with the following fields
      | <name>.txt | <name> |
    When Alice opens the <type> <name>.url
    Then Alice should see the file <name>.txt with textExample

    Examples:
      | type | name      |
      | file | shortcut4 |
