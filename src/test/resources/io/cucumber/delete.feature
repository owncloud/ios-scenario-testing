Feature: Delete item

  Background: User is logged in
    Given I am logged

    @hola
  Scenario: Delete an existent folder
    When I select the folder deleteMe to delete
    And I accept the deletion
    Then I do not see deleteMe in my file list
