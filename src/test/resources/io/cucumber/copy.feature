Feature: Copy item

  Background: User is logged in
    Given user1 is logged

  Scenario: Copy an existent folder to another location
    When user selects the item copyMe to copy
    And user selects Documents as target folder
    Then user sees copyMe in the file list as original
    And user sees copyMe inside the folder Documents