Feature: Download a file in the account

  Background: User is logged in
    Given I am logged
    And There is an item called <itemName> in the account

  Scenario Outline: Download a file that is not previewable
    When I select the folder <itemName> to download
    Then I see the detailed information: <itemName>, <Type>, and <Size>
    And The item <itemName> is downloaded to the device

    Examples:
      | itemName               | Type     | Size   |
      | ownCloud Manual.pdf    | PDF file | 4.8 MB |