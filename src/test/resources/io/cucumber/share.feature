Feature: Share

  Scenario: Correct share
    Given I am logged
    When I select Documents to share
    Then I see the Share view
