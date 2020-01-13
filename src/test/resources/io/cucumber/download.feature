Feature: Download a file in the account

  Background: User is logged in
    Given I am logged
    And There is an item called <itemName> in the account

    @down
  Scenario Outline: Download a file that is not previewable
    When I select the item <itemName> to download
    Then I see the detailed information: <itemName>, <Type>, and <Size>
    And The item <itemName> is stored in the device

    Examples:
      | itemName               | Type     | Size   |
      | ownCloud Manual.pdf    | PDF file | 4.8 MB |

      @down
  Scenario Outline: Download a file that is  previewable
    When I select the item <itemName> to download
    Then Item is opened and previewed
    And The item <itemName> is stored in the device

    Examples:
      | itemName            |
      | San Francisco.jpg   |
