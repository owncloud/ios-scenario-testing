@createfolder
Feature: Create a new folder

  As a user
  I want to create new folders in my account,
  so that i will put order in my file structure

  @smoke
  Scenario: Create a new folder with a correct name
    Given user Alice is logged in
    When Alice selects the option Create Folder
    And Alice sets FolderTest as new name
    Then Alice should see FolderTest in the filelist

  Scenario: Create a new folder with an incorrect name
    Given user Alice is logged in
    And the following items have been created in the account
      | folder   | Documents  |
    #And item Documents is visible
    When Alice selects the option Create Folder
    And Alice sets Documents as new name
    Then Alice should see a duplicated item error