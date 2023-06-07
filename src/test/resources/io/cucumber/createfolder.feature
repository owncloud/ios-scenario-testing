@createfolder
Feature: Create a new folder

  As a user
  I want to create new folders in my account,
  so that i will put order in my file structure

  @smoke
  Scenario Outline: Create a new folder with a correct name
    Given user Alice is logged in
    When Alice selects the option Create Folder
    And Alice sets <item> as new name
    Then Alice should see <item> in the filelist

      Examples:
        | item       |
        | FolderTest |

  Scenario Outline: Create a new folder with an incorrect name
    Given user Alice is logged in
    And the following items have been created in the account
      | folder | <newName> |
    When Alice selects the option Create Folder
    And Alice sets <newName> as new name
    Then Alice should see a duplicated item error

      Examples:
        | newName   |
        | Documents |