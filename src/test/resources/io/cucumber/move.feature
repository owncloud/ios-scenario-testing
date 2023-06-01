@move
Feature: Move item

  As a user
  I want to move content to other location in my account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario: Move an existent folder to another location using the Contextual menu
    Given the following items have been created in the account
      | folder   | move1       |
      | folder   | Documents1  |
    When Alice selects to move the folder move1 using the Contextual menu
    And Alice selects Documents1 as target folder of the move operation
    Then Alice should not see move1 in the filelist anymore
    And Alice should see move1 inside the folder Documents1

  Scenario: Move an existent file to another location using the Contextual menu
    Given the following items have been created in the account
      | file    | move2.txt   |
      | folder  | Documents3  |
    When Alice selects to move the file move2.txt using the Contextual menu
    And Alice selects Documents3 as target folder of the move operation
    Then Alice should not see move2.txt in the filelist anymore
    And Alice should see move2.txt inside the folder Documents3

  Scenario: Move an existent item to a new created folder in the picker
    Given the following items have been created in the account
      | file | move2.txt |
    When Alice selects to move the file move2.txt using the Contextual menu
    And Alice creates new folder move3 in the folder picker to move inside
    Then Alice should not see move2.txt in the filelist anymore
    But Alice should see move2.txt inside the folder move3

  @nooc10
  Scenario: Move an existent item to same location is not allowed
    Given the following items have been created in the account
      | file  | move3.txt |
    When Alice selects to move the file move3.txt using the Actions menu
    And Alice selects / as target folder of the move operation
    Then move action should not be allowed

  Scenario: Move a folder to another place with same item name
    Given the following items have been created in the account
      | folder | move4       |
      | folder | move5       |
      | folder | move4/move5 |
    When Alice selects to move the folder move5 using the Contextual menu
    And Alice selects move4 as target folder of the move operation
    Then Alice should see the following error
      | move5 already exists |