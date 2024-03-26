@avoffline
Feature: Set items as available offline (downloaded and synced)

  As a user
  I want to set content as available offline
  So that the content will be always downloaded and synced

  Background: User is logged in
    Given user Alice is logged in

  Scenario Outline: Set a file as available offline using the Actions menu
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to make available offline the <type> <item> using the <menu> menu
    Then Alice should see the item <item> as av.offline

    Examples:
      | type | item      | menu       |
      | file | file1.pdf | Actions    |
      | file | file2.txt | Contextual |

  Scenario: Inserting a file inside an av.offline folder, turns the file av.offline
    Given the following items have been created in Alice account
      | file   | file3.pdf  |
      | folder | folderTest |
    When Alice selects to make available offline the folder folderTest using the Contextual menu
    And Alice selects to move the file file3.pdf using the Actions menu
    And Alice selects folderTest as target folder of the move operation
    Then Alice should see the item folderTest as av.offline
    And Av. offline option is not available for item folderTest/file3.pdf

  Scenario: Moving a file inside an av.offline folder to a non av.offline folder, it turns not av.offline
    Given the following items have been created in Alice account
      | folder | folderTest2           |
      | file   | folderTest2/file4.txt |
    When Alice selects to make available offline the folder folderTest2 using the Contextual menu
    And Alice selects to move the file folderTest2/file4.txt using the Actions menu
    And Alice selects / as target folder of the move operation
    And Alice browses to root folder
    Then Alice should not see the item file4.txt as av.offline

  @quickaccess
  Rule: Av.offline quick access

  Scenario: Av.offline item is available in "Av.offline" quick access
    Given the following items have been created in Alice account
      | file   | file5.txt |
      | folder | folder5   |
    And Alice selects to make available offline the file file5.txt using the Contextual menu
    And Alice selects to make available offline the folder folder5 using the Contextual menu
    When Alice opens the Available Offline collection of Quick Access
    Then Alice should see file5.txt in Quick Access
    And Alice should see folder5 in Quick Access