@delete
Feature: Delete item

  As an user, i want to be able to delete content from my list
  so that i can get rid of the content i do not need anymore

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | deleteMe |

  Scenario Outline: Delete an existent folder
    When user selects the item <item> to delete
    And user accepts the deletion
    Then user does not see <item> in the file list anymore

    Examples:
      | item      |
      | deleteMe  |
