@rename
Feature: Rename an item

  As a user, i want to rename the items of my account
  so that i see clearer or different names as i need them

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      | RenameMe  |

  @smoke
  Scenario: Rename an item using the Actions menu
    When user selects to rename the item RenameMe using the Actions menu
    And user sets Renamed as new name
    Then user should see Renamed in the filelist
    But user should not see Rename in the filelist anymore

  Scenario: Rename an item using the Contextual menu
    When user selects to rename the item RenameMe using the Contextual menu
    And user sets Renamed as new name
    Then user should see Renamed in the filelist
    But user should not see Rename in the filelist anymore