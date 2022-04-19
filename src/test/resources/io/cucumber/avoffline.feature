@avoffline @ignore
Feature: Set items as available offline (downloaded and synced)

  As a user
  I want to set content as available offline
  So that the content will be always downloaded and synced

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario: Set a file as available offline using the Actions menu
    Given the following items have been created in the account
      | file   | file.pdf  |
    When Alice selects to make available offline the file file.pdf using the Actions menu
    Then Alice should see the item file.pdf as av.offline

  Scenario: Set a file as available offline using the Contextual menu
    Given the following items have been created in the account
      | file   | file2.pdf  |
    When Alice selects to make available offline the file file2.pdf using the Contextual menu
    Then Alice should see the item file2.pdf as av.offline

  Scenario: Inserting a file inside an av.offline folder, turns the file av.offline
    Given the following items have been created in the account
      | file   | file3.pdf  |
      | folder | folderTest |
    When Alice selects to make available offline the folder folderTest using the Actions menu
    And Alice selects to move the file file3.pdf using the Actions menu
    And Alice selects folderTest as target folder of the move operation
    Then Alice should see the item folderTest/file3.pdf as av.offline