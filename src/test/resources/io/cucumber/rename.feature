@rename
Feature: Rename an item

  As an user, i want to rename the items of my account
  so that i see clearer or different names as i need them

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | RenameMe  |

  Scenario Outline: Rename an item
    When user selects the item <item> to rename
    And user sets <itemRenamed> as name
    Then user sees <itemRenamed> in the file list
    And user does not see <item> in the file list anymore

    Examples:
      |  item       |  itemRenamed  |
      |  RenameMe   |  Renamed      |