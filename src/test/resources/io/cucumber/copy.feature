Feature: Copy item

  Background: User is logged in
    Given I am logged
    
  Scenario: Copy an existent folder to another location
    When I select the folder copyMe to copy
    And I select Documents as target folder
    Then I see copyMe in my file list as original
    And I see copyMe inside the folder Documents