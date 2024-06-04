@sidebar
Feature: Sidebar

  As a user
  I want to have a shortcut to some folders
  So that, i can open them quickly

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario: Add folder to sidebar
    Given the following items have been created in Alice account
      | folder | sidebar1              |
      | file   | sidebar1/sidebar1.txt |
    When Alice selects to add to the sidebar the folder sidebar1 using the Actions menu
    And Alice opens the sidebar
    And Alice opens the folder sidebar1 in sidebar
    Then Alice should see sidebar1.txt in filelist

  Scenario: Remove file from sidebar
    Given the following items have been created in Alice account
      | folder | sidebar2 |
    When Alice selects to add to the sidebar the folder sidebar2 using the Contextual menu
    And Alice selects to remove from the sidebar the folder sidebar2 using the Actions menu
    And Alice opens the sidebar
    Then Alice should not see sidebar2 in sidebar
