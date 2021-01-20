@avoffline
Feature: Set items as available offline (downloaded and synced)

  As an user, i want to be able to set content as available offline
  so that the content is download and synced

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | ownCloud Manual.pdf |

  Scenario Outline: Set a file as available offline
    When user selects to Set as available offline the item <itemName>
    Then user sees the item <itemName> as av.offline
    And the item <itemName> is stored in the device

    Examples:
      | itemName             |
      | ownCloud Manual.pdf  |