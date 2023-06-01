@copy
Feature: Copy item

  As a user
  I want to copy files between different locations of my account
  So that i have different copies in different places that i can modify separately

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario: Copy an existent folder to another location using the Actions menu
    Given the following items have been created in the account
      | folder  | copy1      |
      | folder  | Documents1 |
    When Alice selects to copy the folder copy1 using the Actions menu
    And Alice selects Documents1 as target folder of the copy operation
    Then Alice should see copy1 in the filelist
    And Alice should see copy1 inside the folder Documents1

  Scenario: Copy an existent file to another location using the Contextual menu
    Given the following items have been created in the account
      | file    | copy2.txt  |
      | folder  | Documents2 |
    When Alice selects to copy the file copy2.txt using the Actions menu
    And Alice selects Documents2 as target folder of the copy operation
    Then Alice should see copy2.txt in the filelist
    And Alice should see copy2.txt inside the folder Documents2

  Scenario: Copy an existent item to a new created folder in the picker
    Given the following items have been created in the account
      | file | copy4.txt |
    When Alice selects to copy the file copy4.txt using the Actions menu
    And Alice creates new folder copy5 in the folder picker to copy inside
    Then Alice should see copy4.txt inside the folder copy5

  Scenario: Copy a folder to another place with same item name
    Given the following items have been created in the account
      | folder | copy5       |
      | folder | copy6       |
      | folder | copy5/copy6 |
    When Alice selects to copy the folder copy6 using the Contextual menu
    And Alice selects copy5 as target folder of the copy operation
    Then Alice should see the following error
      | copy6 already exists |

  Scenario: Copy an existent item to same location is not allowed
    Given the following items have been created in the account
      | file | copy7.txt |
    When Alice selects to copy the file copy7.txt using the Actions menu
    And Alice selects / as target folder of the copy operation
    Then copy action should not be allowed