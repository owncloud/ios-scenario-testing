@rename
Feature: Rename an item

  As a user, i want to rename the items of my account
  so that i see clearer or different names as i need them

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario: Rename an item using the Actions menu
    Given the following items have been created in the account
      | item   | rename1  |
    When Alice selects to rename the item rename1 using the Actions menu
    And Alice sets renamed1 as new name
    Then Alice should see renamed1 in the filelist
    And Alice should not see rename1 in the filelist anymore

  Scenario: Rename an item using the Contextual menu
    Given the following items have been created in the account
      | item   | rename2  |
    When Alice selects to rename the item rename2 using the Contextual menu
    And Alice sets renamed2 as new name
    Then Alice should see renamed2 in the filelist
    And Alice should not see rename2 in the filelist anymore