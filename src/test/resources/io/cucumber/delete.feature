@delete
Feature: Delete item

  As a user
  I want to be able to delete content from my account
  so that i can get rid of the files and folders i do not need anymore

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario: Delete the only item (folder) using the Actions menu
    Given the following items have been created in the account
      | folder  | delete1  |
    When Alice selects to delete the folder delete1 using the Actions menu
    And Alice confirms the deletion
    Then Alice should not see delete1 in the filelist anymore
    And Alice should see an empty list of files

  Scenario: Delete an existent folder using the Contextual menu
    Given the following items have been created in the account
      | folder  | delete2      |
      | file    | delete3.txt  |
    When Alice selects to delete the folder delete2 using the Contextual menu
    And Alice confirms the deletion
    Then Alice should not see delete2 in the filelist anymore
    And Alice should see delete3.txt in the filelist

  @ignore
  Scenario: Delete an existent file using the Swipe menu
    Given the following items have been created in the account
      | file  | delete4.txt  |
    When Alice selects to delete the folder delete4.txt using the Swipe menu
    And Alice confirms the deletion
    Then Alice should not see delete4.txt in the filelist anymore
    And Alice should see an empty list of files