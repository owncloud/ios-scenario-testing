@copy
Feature: Copy item

  As a user
  I want to copy files between different locations of my account
  So that i have different copies in different places that i can modify separately

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      | copyMe    |
      | Documents |

  Scenario: Copy an existent folder to another location using the Actions menu
    When user selects to copy the item copyMe using the Actions menu
    And user selects Documents as target folder of the copy
    Then user should see copyMe in the filelist
    And user should see copyMe inside the folder Documents

  Scenario: Copy an existent folder to another location using the Contextual menu
    When user selects to copy the item copyMe using the Contextual menu
    And user selects Documents as target folder of the copy
    Then user should see copyMe in the filelist
    And user should see copyMe inside the folder Documents