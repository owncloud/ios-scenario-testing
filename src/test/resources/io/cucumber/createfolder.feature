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