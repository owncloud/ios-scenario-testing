@rename @ignore
Feature: Rename an item

  As a user, i want to rename the items of my account
  so that i see clearer or different names as i need them

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario Outline: Rename an item
    Given the following items have been created in the account
      | <type> | <originalName> |
    When Alice selects to rename the <type> <originalName> using the <menu> menu
    And Alice sets <newName> as new name
    Then Alice should see <newName> in the filelist
    And Alice should not see <originalName> in the filelist anymore

    Examples:
      | type   | originalName | newName     | menu       |
      | folder | rename1      | rename2     | Actions    |
      | file   | rename3.txt  | rename4.txt | Contextual |

  #Check notifications fix
  Scenario Outline: Rename an item using the Actions menu with an existing name
    Given the following items have been created in the account
      | <type> | <originalName> |
      | <type> | <newName>      |
    When Alice selects to rename the <type> <originalName> using the Actions menu
    And Alice sets <newName> as new name
    Then Alice should see a duplicated item error

    Examples:
      | type   | originalName | newName |
      | folder | rename5      | rename6 |