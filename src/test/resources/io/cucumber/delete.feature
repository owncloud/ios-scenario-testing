@delete
Feature: Delete item

  As a user
  I want to be able to delete content from my account
  so that i can get rid of the files and folders i do not need anymore

  Background: User is logged in
    Given user Alice is logged in

  Scenario: Delete an existent folder using the Actions menu
    Given the folder delete1 has been created in the account
    When Alice selects to delete the folder delete1 using the Actions menu
    And Alice confirms the deletion
    Then Alice should not see delete1 in the filelist anymore

  Scenario: Delete an existent folder using the Contextual menu
    Given the folder delete2 has been created in the account
    When Alice selects to delete the folder delete2 using the Contextual menu
    And Alice confirms the deletion
    Then Alice should not see delete2 in the filelist anymore

  Scenario: Delete an existent folder using the Swipe menu
    Given the folder delete3 has been created in the account
    When Alice selects to delete the folder delete3 using the Swipe menu
    And Alice confirms the deletion
    Then Alice should not see delete3 in the filelist anymore