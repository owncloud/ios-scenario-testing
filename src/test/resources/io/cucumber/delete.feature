@delete
Feature: Delete item

  As a user
  I want to be able to delete content from my account
  so that i can get rid of the files and folders i do not need anymore

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      | deleteMe |

  Scenario: Delete an existent folder using the Actions menu
    When user selects to delete the file deleteMe using the Actions menu
    And user confirms the deletion
    Then user should not see deleteMe in the filelist anymore

  Scenario: Delete an existent folder using the Contextual menu
    When user selects to delete the file deleteMe using the Contextual menu
    And user confirms the deletion
    Then user should not see deleteMe in the filelist anymore