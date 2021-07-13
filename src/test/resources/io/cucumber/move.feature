@move
Feature: Move item

  As a user
  I want to move content to other location in my account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user Alice is logged in
    And the following items have been created in the account
      | Documents  |

  Scenario: Move an existent folder to another location using the Actions menu
    Given the folder move1 has been created in the account
    When Alice selects to move the folder move1 using the Actions menu
    And Alice selects Documents as target folder of the move operation
    Then Alice should not see move1 in the filelist anymore
    And Alice should see move1 inside the folder Documents

  Scenario: Move an existent folder to another location using the Contextual menu
    Given the folder move2 has been created in the account
    When Alice selects to move the folder move2 using the Contextual menu
    And Alice selects Documents as target folder of the move operation
    Then Alice should not see move2 in the filelist anymore
    And Alice should see move2 inside the folder Documents
