Feature: Download a file in the account

  As an user, i want to be able to download items locally
  so that the content is stored in the device

  Background: User is logged in
    Given user1 is logged
    And there is an item called <itemName> in the account

  Scenario Outline: Download a file that is not previewable
    When user selects the item <itemName> to download
    Then user sees the detailed information: <itemName>, <Type>, and <Size>
    And the item <itemName> is stored in the device

    Examples:
      | itemName               | Type     | Size   |
      | ownCloud Manual.pdf    | PDF file | 4.8 MB |

  Scenario Outline: Download a file that is  previewable
    When user selects the item <itemName> to download
    Then the item is opened and previewed
    And the item <itemName> is stored in the device

    Examples:
      | itemName            |
      | San Francisco.jpg   |
