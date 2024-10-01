@copy
Feature: Copy item

  As a user
  I want to copy files between different locations of my account
  So that i have different copies in different places that i can modify separately

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario Outline: Copy an existent folder to another location using the Actions menu
    Given the following items have been created in Alice account
      | <type> | <item>   |
      | folder | <target> |
    When Alice selects to copy the <type> <item> using the <menu> menu
    And Alice selects <target> as target folder of the copy operation
    Then Alice should see <item> in the filelist
    And Alice should see <item> inside the folder <target>

    Examples:
      | type   | item      | target      | menu       |
      | folder | copy1     | copy1target | Actions    |
      | file   | copy2.txt | copy2target | Contextual |

  Scenario Outline: Copy an existent item to a new created folder in the picker
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to copy the <type> <item> using the <menu> menu
    And Alice creates new folder <target> in the folder picker to copy inside
    Then Alice should see <item> inside the folder <target>

    Examples:
      | type | item      | target      | menu    |
      | file | copy3.txt | copy3target | Actions |

  @ignore
  Scenario: Copy a folder to another place with same item name
    Given the following items have been created in Alice account
      | folder | copy4             |
      | folder | copy4target       |
      | folder | copy4target/copy4 |
    When Alice selects to copy the folder copy4 using the Contextual menu
    And Alice selects copy4target as target folder of the copy operation
    Then Alice should see the following error
      | copy4 already exists |

  @nooc10
  Scenario Outline: Copy an existent item to same location is not allowed
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to copy the <type> <item> using the <menu> menu
    And Alice selects <target> as target folder of the copy operation
    Then copy action should not be allowed

    Examples:
      | type | item      | target | menu    |
      | file | copy5.txt | /      | Actions |