@copy
Feature: Copy item

  As an user, i want to copy files inside my account
  so that i have different copies in different places that i can modify separately

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | copyMe |

  Scenario Outline: Copy an existent folder to another location
    When user selects to Copy the item <itemName>
    And user selects <destination> as target folder
    Then user sees <itemName> in the file list as original
    And user sees <itemName> inside the folder <destination>

    Examples:
      | itemName | destination |
      | copyMe   | Documents   |