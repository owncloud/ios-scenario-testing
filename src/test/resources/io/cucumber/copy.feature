@copy
Feature: Copy item

  As a user
  I want to copy files between different locations of my account
  So that i have different copies in different places that i can modify separately

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      | Documents |

  Scenario: Copy an existent folder to another location using the Actions menu
    Given the folder copy1 has been created in the account
    When user selects to copy the folder copy1 using the Actions menu
    And user selects Documents as target folder of the copy operation
    Then user should see copy1 inside the folder Documents
    And user should see copy1 in the filelist

  Scenario: Copy an existent folder to another location using the Contextual menu
    Given the folder copy2 has been created in the account
    When user selects to copy the folder copy2 using the Contextual menu
    And user selects Documents as target folder of the copy operation
    Then user should see copy2 inside the folder Documents
    And user should see copy2 in the filelist
