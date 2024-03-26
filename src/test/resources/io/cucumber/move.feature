@move
Feature: Move item

  As a user
  I want to move content to other location in my account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario Outline: Move an existent folder to another location using the Contextual menu
    Given the following items have been created in Alice account
      | <itemType> | <itemName>     |
      | folder     | <targetFolder> |
    When Alice selects to move the <itemType> <itemName> using the <menu> menu
    And Alice selects <targetFolder> as target folder of the move operation
    Then Alice should not see <itemName> in the filelist anymore
    And Alice should see <itemName> inside the folder <targetFolder>

    Examples:
      | itemType | itemName | targetFolder | menu       |
      | folder   | move1    | Documents1   | Contextual |

  Scenario Outline: Move an existent file to another location using the Contextual menu
    Given the following items have been created in Alice account
      | <itemType> | <itemName>     |
      | folder     | <targetFolder> |
    When Alice selects to move the <itemType> <itemName> using the <menu> menu
    And Alice selects <targetFolder> as target folder of the move operation
    Then Alice should not see <itemName> in the filelist anymore
    And Alice should see <itemName> inside the folder <targetFolder>

    Examples:
      | itemType | itemName  | targetFolder | menu       |
      | file     | move2.txt | Documents3   | Contextual |

  Scenario Outline: Move an existent item to a new created folder in the picker
    Given the following items have been created in Alice account
      | <itemType> | <itemName> |
    When Alice selects to move the <itemType> <itemName> using the <menu> menu
    And Alice creates new folder <targetFolder> in the folder picker to move inside
    Then Alice should not see <itemName> in the filelist anymore
    But Alice should see <itemName> inside the folder <targetFolder>

    Examples:
      | itemType | itemName  | targetFolder | menu       |
      | file     | move4.txt | move5        | Contextual |

  @nooc10
  Scenario Outline: Move an existent item to same location is not allowed
    Given the following items have been created in Alice account
      | <itemType> | <itemName> |
    When Alice selects to move the <itemType> <itemName> using the <menu> menu
    And Alice selects <targetFolder> as target folder of the move operation
    Then move action should not be allowed

    Examples:
      | itemType | itemName  | targetFolder | menu    |
      | file     | move6.txt | /            | Actions |

  Scenario: Move a folder to another place with same item name
    Given the following items have been created in Alice account
      | folder | move7       |
      | folder | move8       |
      | folder | move7/move8 |
    When Alice selects to move the folder move8 using the Contextual menu
    And Alice selects move7 as target folder of the move operation
    Then Alice should see the following error
      | move8 already exists |