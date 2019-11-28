Feature: Create a new folder

  Background: User is logged in
    Given I am logged

  Scenario: Create a new folder
    When I select the option Create Folder
    And I set FolderTest as name
    Then I see FolderTest in my file list