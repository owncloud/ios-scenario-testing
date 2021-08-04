@copy
Feature: Copy item

  As a user
  I want to copy files between different locations of my account
  So that i have different copies in different places that i can modify separately

  Background: User is logged in
    Given user Alice is logged in
    And the following items have been created in the account
      | folder | Documents2 |

  Scenario: Copy an existent folder to another location using the Actions menu
    Given the following items have been created in the account
      | folder  | copy1 |
    When Alice selects to copy the folder copy1 using the Actions menu
    And Alice selects Documents2 as target folder of the copy operation
    Then Alice should see copy1 inside the folder Documents2
    And Alice should see copy1 in the filelist

  Scenario: Copy an existent folder to another location using the Contextual menu
    Given the following items have been created in the account
      | folder  | copy2 |
    When Alice selects to copy the folder copy2 using the Contextual menu
    And Alice selects Documents2 as target folder of the copy operation
    Then Alice should see copy2 inside the folder Documents2
    And Alice should see copy2 in the filelist
