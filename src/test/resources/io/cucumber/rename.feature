@rename
Feature: Rename an item

  As a user, i want to rename the items of my account
  so that i see clearer or different names as i need them

  Background: User is logged in
    Given user user1 is logged

  @smoke
  Scenario: Rename an item using the Actions menu
    Given the item rename1 has been created in the account
    When user selects to rename the item rename1 using the Actions menu
    And user sets renamed1 as new name
    Then user should see renamed1 in the filelist
    And user should not see rename1 in the filelist anymore

  Scenario: Rename an item using the Contextual menu
    Given the item rename2 has been created in the account
    When user selects to rename the item rename2 using the Contextual menu
    And user sets renamed2 as new name
    Then user should see renamed2 in the filelist
    And user should not see rename2 in the filelist anymore