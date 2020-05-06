@createfolder
Feature: Create a new folder

  As an user, i want to be able to create new folders,
  so that i can put order in my file structure

  Background: User is logged in
    Given user1 is logged

  Scenario Outline: Create a new folder
    When user selects the option Create Folder
    And user sets <newFolder> as name
    Then user sees <newFolder> in the file list

    Examples:
      | newFolder   |
      | FolderTest  |