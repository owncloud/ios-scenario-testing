@createfolder
Feature: Create a new folder

  As a user
  I want to create new folders in my account,
  so that i will put order in my file structure

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario Outline: Create a new folder with a correct name
    When Alice selects the option Create Folder
    And Alice sets <item> as new name
    Then Alice should see <item> in the filelist

    Examples:
      | item       |
      | FolderTest |

  Scenario Outline: Create a new folder with an incorrect name
    And the following items have been created in Alice account
      | folder | <newName> |
    When Alice selects the option Create Folder
    And Alice sets <newName> as new name
    Then Alice should see a duplicated item error

    Examples:
      | newName   |
      | Documents |