@move
Feature: Move item

  As a user
  I want to move content to other location in my account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      | folderMove |
      | Documents  |

  Scenario: Move an existent folder to another location using the Actions menu
    When user selects to move the item folderMove using the Actions menu
    And user selects Documents as target folder of the move
    Then user should not see folderMove in the filelist anymore
    And user should see folderMove inside the folder Documents

  Scenario: Move an existent folder to another location using the Contextual menu
    When user selects to move the item folderMove using the Contextual menu
    And user selects Documents as target folder of the move
    Then user should not see folderMove in the filelist anymore
    And user should see folderMove inside the folder Documents
