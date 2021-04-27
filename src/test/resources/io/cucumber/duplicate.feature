@duplicate
Feature: Duplicate item

  As a user
  I want to duplicate items in my account
  So that i will have a copy in the same place to perform modifications

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      | DuplicateFolder |

  Scenario: Duplicate an existent folder using the Actions menu
    When user selects to duplicate the item DuplicateFolder using the Actions menu
    Then user should see DuplicateFolder in the filelist
    And user should see DuplicateFolder 2 in the filelist

  Scenario: Duplicate an existent folder using the Contextual menu
    When user selects to duplicate the item DuplicateFolder using the Contextual menu
    Then user should see DuplicateFolder in the filelist
    And user should see DuplicateFolder 2 in the filelist