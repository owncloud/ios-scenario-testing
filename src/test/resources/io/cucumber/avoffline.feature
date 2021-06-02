@avoffline
Feature: Set items as available offline (downloaded and synced)

  As a user
  I want to set content as available offline
  So that the content will be always downloaded and synced

  Background: User is logged in
    Given user user1 is logged

  Scenario: Set a file as available offline using the Actions menu
    Given the file file.pdf has been created in the account
    When user selects to make available offline the item file.pdf using the Actions menu
    Then user should see the item file.pdf as av.offline

  Scenario: Set a file as available offline using the Contextual menu
    Given the file file2.pdf has been created in the account
    When user selects to make available offline the item file2.pdf using the Contextual menu
    Then user should see the item file2.pdf as av.offline