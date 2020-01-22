Feature: Set items as available offline (downloaded and synced)

  Background: User is logged in
    Given I am logged
    And There is an item called <itemName> in the account

    @avo
  Scenario Outline: Set a file as available offline
    When I select the item <itemName> to av.offline
    Then I see the item <itemName > as av.offline
    And The item <itemName> is stored in the device

    Examples:
      | itemName             |
      | ownCloud Manual.pdf  |