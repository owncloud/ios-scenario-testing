@shares
Feature: Private Share

  As a user
  I want to share my content with other users in the platform
  So that the content is accessible and others can contribute

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      | Documents        |
      | Files            |
      | textExample.txt  |

  @share1
  Scenario: Correct share with user
    When user selects to share the folder Files
    And user selects user user2 as sharee
    Then user user2 should have access to Files
    And share should be created on Files with the following fields
      | sharee | user2 |

  @group
  Scenario: Correct share with group
    When user selects to share the folder Files
    And user selects group test as sharee
    Then group including user2 should have access to Files
    And share should be created on Files with the following fields
      | group | test |

  @federated
  Scenario: Correct federated share
    When user selects to share the file textExample.txt
    And user selects user demo@demo.owncloud.com as sharee
    Then share should be created on textExample.txt with the following fields
      | sharee | demo@demo.owncloud.com |

    @deletes
  Scenario: Delete existing share
    Given the item Files has been already shared with user2
    When user selects to edit share the folder Files
    And user deletes the share
    Then user user2 should not have access to Files
    And Files should not be shared anymore with user2