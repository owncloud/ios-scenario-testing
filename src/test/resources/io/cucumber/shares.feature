@shares
Feature: Private Share

  As a user
  I want to share my content with other users in the platform
  So that the content is accessible and others can contribute

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario Outline: Correct share with user
    Given the <type> <item> has been created in the account
    When Alice selects to share the <type> <item> using the Actions menu
    And Alice selects user Bob as sharee with default permissions
    Then user Bob should have access to <item>
    And share should be created on <item> with the following fields
      | sharee | Bob |

    Examples:
      |  type   |  item        |
      |  file   |  Share1.txt  |
      |  folder |  Share2      |

  @smoke
  Scenario Outline: Correct share with group
    Given the <type> <item> has been created in the account
    When Alice selects to share the <type> <item> using the Actions menu
    And Alice selects group test as sharee with default permissions
    Then group test should have access to <item>
    And share should be created on <item> with the following fields
      | group | test |

    Examples:
      |  type   |  item        |
      |  file   |  Share3.txt  |
      |  folder |  Share4      |

  Scenario Outline: Correct share with user using the Contextual menu
    Given the <type> <item> has been created in the account
    When Alice selects to share the <type> <item> using the Contextual menu
    And Alice selects user Bob as sharee with default permissions
    Then user Bob should have access to <item>
    And share should be created on <item> with the following fields
      | sharee | Bob |

    Examples:
      |  type   |  item        |
      |  file   |  Share5.txt  |
      |  folder |  Share6      |

  Scenario Outline: Correct federated share
    Given the <type> <item> has been created in the account
    When Alice selects to share the <type> <item> using the Actions menu
    And Alice selects user demo@demo.owncloud.com as sharee with default permissions
    Then share should be created on <item> with the following fields
      | sharee | demo@demo.owncloud.com |

    Examples:
      |  type   |  item        |
      |  file   |  Share7.txt  |
      |  folder |  Share8      |

  Scenario Outline: Edit existing share on a folder, removing permissions
    Given the folder <item> has been created in the account
    And Alice has shared folder <item> with <user> with permissions 31
    When Alice selects to edit share the folder <item> using the Actions menu
    And Alice edits the share on <item> with permissions <permissions>
    Then user <user> should have access to <item>
    And share should be created on <item> with the following fields
      | sharee        |  <user>        |
      | permissions   |  <permissions> |

#Permissions
    # READ -> 1
    # UPDATE -> 2
    # CREATE -> 4
    # DELETE -> 8
    # SHARE -> 16

    Examples:
      |  item     |   user  | permissions |
      |  Share9   |   Bob   |   1         |
      |  Share10  |   Bob   |   9         |
      |  Share11  |   Bob   |   13        |
      |  Share12  |   Bob   |   17        |

  Scenario: Reshare allowed
    Given the file Share13.txt has been created in the account
    When Alice selects to share the file Share13.txt using the Actions menu
    And Alice selects user Bob as sharee with default permissions
    And Bob shares file Share13.txt with Charles with permissions 31
    Then user Bob should have access to Share13.txt
    And user Charles should have access to Share13.txt
    And share should be created on Share13.txt with the following fields
      | sharee  | Bob       |
      | sharee  | Charles   |

  Scenario: Reshare not allowed
    Given the file Share14.txt has been created in the account
    When Alice selects to share the file Share14.txt using the Actions menu
    And Alice selects user Bob as sharee without share permission
    And Bob shares file Share14.txt with Charles with permissions 31
    Then user Bob should have access to Share14.txt
    But user Charles should not have access to Share14.txt

  Scenario: Delete existing share on folder
    Given the folder Share15 has been created in the account
    And Alice has shared folder Share15 with Bob with permissions 31
    When Alice selects to edit share the folder Share15 using the Actions menu
    And Alice deletes the share
    Then user Bob should not have access to Share15
    And Share15 should not be shared anymore with Bob