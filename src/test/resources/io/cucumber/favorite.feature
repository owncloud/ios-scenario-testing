@favorite @ignore
Feature: Set an item as favorite (starred)

  As a user, i want to set a file or folder as favorite
  so that it will be quickly accessed by shortcut

  Background: User is logged in
    Given user Alice is logged in

  @setfavorite
  Rule: Set favorite

  Scenario: Favorite an item using the Actions menu
    Given the following items have been created in Alice account
      | folder | fav1 |
    When Alice sets as favorite the folder fav1 using the Actions menu
    And Alice closes the Actions menu
    Then folder fav1 should be set as favorite

  @smoke
  Scenario: Favorite an item using the Contextual menu
    Given the following items have been created in Alice account
      | file | fav2.txt |
    When Alice sets as favorite the file fav2.txt using the Contextual menu
    Then file fav2.txt should be set as favorite

  @unsetfavorite
  Rule: Unset favorite

  Scenario Outline: Unfavorite an item using the Actions menu
    Given the following items have been created in Alice account
      | <type> | <item> |
    And item <item> has been set as favorite
    When Alice sets as unfavorite the <type> <item> using the <menu> menu
    And Alice closes the <menu> menu
    Then <type> <item> should be set as unfavorite

    Examples:

      | type   | item     | menu       |
      | folder | fav3     | Actions    |
      | file   | fav4.txt | Contextual |

  @quickaccess
  Rule: Favorites quick access

  Scenario: Favorite item is available in "Favorites" quick access
    Given the following items have been created in Alice account
      | file   | fav5.txt |
      | folder | fav6     |
    And Alice sets as favorite the file fav5.txt using the Actions menu
    And Alice sets as favorite the folder fav6 using the Contextual menu
    When Alice opens the sidebar
    And Alice opens the Favorites collection of Quick Access
    Then Alice should see fav5.txt in Quick Access
    And Alice should see fav6 in Quick Access

  Scenario: Favorite item is not available in "Favorites" quick access anymore after unfavoriting
    Given the following items have been created in Alice account
      | file   | fav7.txt |
      | folder | fav8     |
    And item fav7.txt has been set as favorite
    And item fav8 has been set as favorite
    And Alice sets as unfavorite the file fav7.txt using the Actions menu
    And Alice sets as unfavorite the folder fav8 using the Contextual menu
    When Alice opens the sidebar
    And Alice opens the Favorites collection of Quick Access
    Then Alice should not see fav7.txt in Quick Access
    And Alice should not see fav8 in Quick Access
