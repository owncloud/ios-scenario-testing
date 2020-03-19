Feature: Move item

  As an user, i want to be able to move content to other place in the account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | folderMove |

  Scenario Outline: Move an existent folder to another location
    When user selects the item <itemName> to move
    And user selects <destination> as target folder
    Then user does not see <itemName> in the file list anymore
    And user sees <itemName> inside the folder Documents

    Examples:
      | itemName    | destination |
      | folderMove  | Documents   |