Feature: Move item

  Background: User is logged in
    Given I am logged

    @hola
  Scenario: Move an existent folder to another location
    When I select the item moveMe to move
    And I select Documents as target folder
    Then I do not see moveMe in my file list
    And I see moveMe inside the folder Documents
