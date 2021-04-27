@avoffline
Feature: Set items as available offline (downloaded and synced)

  As a user
  I want to set content as available offline
  So that the content will be always downloaded and synced

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      | ownCloud Manual.pdf |

  Scenario: Set a file as available offline using the Actions menu
    When user selects to make available offline the item ownCloud Manual.pdf using the Actions menu
    Then user should see the item ownCloud Manual.pdf as av.offline

  Scenario: Set a file as available offline using the Contextual menu
    When user selects to make available offline the item ownCloud Manual.pdf using the Contextual menu
    Then user should see the item ownCloud Manual.pdf as av.offline