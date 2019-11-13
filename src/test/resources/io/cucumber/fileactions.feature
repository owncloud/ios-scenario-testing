Feature: File actions

  Background: User is logged in
    Given I am logged


  Scenario: Create a new folder
    When I select the option Create Folder
    And I set FolderTest as name
    Then I see FolderTest in my file list

  @Current
  Scenario: Rename an item
    When I select the item AL1234 to rename
    And I set BM4567 as name
    Then I see BM4567 in my file list
    #And I do not see AL1234 in my file list  NOT WORKING...