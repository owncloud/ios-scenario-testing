Feature: Delete item

  Background: User is logged in
    Given I am logged

    @TT
  Scenario: Delete an existent folder
    When I select the item deleteMe to delete
    And I accept the deletion
    Then I do not see deleteMe in my file list
