Feature: File actions

  Background: User is logged in
    Given I am logged

  @SmokeTest
  Scenario: Create a new folder
    When I select the option Create Folder
    And I set FolderTest as name of the new folder
    Then I see FolderTest in my file list
